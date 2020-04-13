package com.robohorse.robopojogenerator.delegates.file

import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.ProjectModel
import java.util.*

class KotlinFileWriterDelegate(
        messageDelegate: MessageDelegate,
        factory: PostProcessorFactory,
        fileWriterDelegate: FileWriterDelegate,
        private val kotlinDataClassPostProcessor: KotlinDataClassPostProcessor
) : BaseWriterDelegate(messageDelegate, factory, fileWriterDelegate) {

    override fun writeFiles(
            set: Set<ClassItem>,
            generationModel: GenerationModel,
            projectModel: ProjectModel
    ) {
        val imports = resolveImports(set, generationModel)
        val targets = set.toMutableList()
        targets.firstOrNull { it.className == generationModel.rootClassName }?.let {
            val index = targets.indexOf(it)
            targets.removeAt(index)
            targets.add(FIRST_TARGET_POSITION, it)
        }
        val rootClassBuilder = StringBuilder()
        targets.forEachIndexed { index, it ->
            it.apply {
                it.classImports.clear()
                it.packagePath = null
                if (index > 0) {
                    rootClassBuilder.append(ClassTemplate.NEW_LINE)
                }
                rootClassBuilder.append(prepareClass(it, generationModel))
            }
        }
        val classBody = kotlinDataClassPostProcessor.createClassItemText(
                packagePath = projectModel.packageName,
                classTemplate = rootClassBuilder.toString(),
                imports = kotlinDataClassPostProcessor.proceedClassImports(imports).toString()
        )
        writeFile(
                className = generationModel.rootClassName,
                classItemBody = classBody,
                generationModel = generationModel,
                projectModel = projectModel
        )
    }

    private fun resolveImports(
            set: Set<ClassItem>, generationModel: GenerationModel
    ): HashSet<String> {
        val imports = HashSet<String>().apply {
            set.forEach { addAll(it.classImports) }
        }
        val universalClassItem = ClassItem()
        kotlinDataClassPostProcessor.applyAnnotations(generationModel.annotationEnum, universalClassItem)
        imports.addAll(universalClassItem.classImports)
        return imports
    }
}

private const val FIRST_TARGET_POSITION = 0
