package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.filewriter.FILE_JAVA
import com.robohorse.robopojogenerator.filewriter.FILE_KOTLIN
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.parser.InputDataParser
import com.robohorse.robopojogenerator.parser.JsonArrayParser
import com.robohorse.robopojogenerator.parser.JsonObjectParser
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.JavaRecordsPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.utils.MoshiAnnotationsProcessor
import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.properties.templates.ClassTemplate
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.utils.ProcessingModelResolver
import java.io.File

class GeneratorFacade {

    private val classGenerateHelper = ClassGenerateHelper()
    private val classTemplateHelper = ClassTemplateHelper(classGenerateHelper)
    private val moshiAnnotationsProcessor = MoshiAnnotationsProcessor(classGenerateHelper)
    private val jsonObjectParser = JsonObjectParser(classGenerateHelper)
    private val jsonArrayParser = JsonArrayParser(classGenerateHelper)
    private val inputDataParser = InputDataParser(classGenerateHelper, jsonObjectParser, jsonArrayParser)
    private val processingModelResolver = ProcessingModelResolver()
    private val generator = RoboPOJOGenerator(inputDataParser, processingModelResolver)
    private val kotlinDataClassPostProcessor = KotlinDataClassPostProcessor(classGenerateHelper, classTemplateHelper, moshiAnnotationsProcessor)
    private val postProcessorFactory = PostProcessorFactory(
        kotlinDataClassPostProcessor,
        AutoValueClassPostProcessor(classGenerateHelper, classTemplateHelper),
        CommonJavaPostProcessor(classGenerateHelper, classTemplateHelper),
        JavaRecordsPostProcessor(classGenerateHelper, classTemplateHelper)
    )

    fun generate(model: GenerationModel, outputDir: File, packageName: String? = null) {
        val classItems = generator.generate(model)
        if (model.useKotlin && model.useKotlinSingleDataClass) {
            writeSingleKotlinFile(classItems, model, outputDir, packageName)
        } else {
            writeMultipleFiles(classItems, model, outputDir, packageName)
        }
    }

    private fun writeMultipleFiles(
        classItems: Set<ClassItem>,
        model: GenerationModel,
        outputDir: File,
        packageName: String?
    ) {
        val postProcessor = postProcessorFactory.createPostProcessor(model)
        for (classItem in classItems) {
            val className = classItem.className ?: continue
            classItem.packagePath = packageName
            val body = applyIndentation(model,postProcessor.proceed(classItem, model))
            val ext = if (model.useKotlin) FILE_KOTLIN else FILE_JAVA
            File(outputDir, "$className$ext").writeText(body)
        }
    }

    private fun writeSingleKotlinFile(
        classItems: Set<ClassItem>,
        model: GenerationModel,
        outputDir: File,
        packageName: String?
    ) {
        val imports = HashSet<String>().apply {
            classItems.forEach { addAll(it.classImports) }
        }
        val universalClassItem = ClassItem()
        kotlinDataClassPostProcessor.applyAnnotations(model, universalClassItem)
        imports.addAll(universalClassItem.classImports)

        val targets = classItems.toMutableList()
        targets.firstOrNull { it.className == model.rootClassName }?.let {
            targets.remove(it)
            targets.add(0, it)
        }
        val rootClassBuilder = StringBuilder()
        targets.forEachIndexed { index, classItem ->
            classItem.classImports.clear()
            classItem.packagePath = null
            if (index > 0) {
                rootClassBuilder.append(ClassTemplate.NEW_LINE)
            }
            rootClassBuilder.append(
                postProcessorFactory.createPostProcessor(model).proceed(classItem, model)
            )
        }
        val classBody = kotlinDataClassPostProcessor.createClassItemText(
            packagePath = packageName,
            classTemplate = rootClassBuilder.toString(),
            imports = kotlinDataClassPostProcessor.proceedClassImports(imports, model).toString()
        )
        val body = applyIndentation(model,classBody)
        File(outputDir, "${model.rootClassName}$FILE_KOTLIN").writeText(body)
    }

    private fun applyIndentation(model: GenerationModel, body: String): String =
        if (!model.useTabsIndentation) body.replace("\t", "    ") else body
}
