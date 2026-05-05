package com.robohorse.robopojogenerator.cli

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.robohorse.robopojogenerator.GeneratorFacade
import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.JavaStyle
import java.io.File
import kotlin.system.exitProcess

class GenerationModelDeserializer : JsonDeserializer<GenerationModel>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): GenerationModel {
        val node = p.codec.readTree<JsonNode>(p)
        return GenerationModel(
            rewriteClasses = true,
            useKotlin = node.path("useKotlin").asBoolean(false),
            annotationEnum = FrameworkVW.fromString(
                node.path("framework").asText("none"),
                style = JavaStyle.valueOf(node.path("javaStyle").asText("CLASS").uppercase())
            ),
            rootClassName = node.get("rootClassName").asText(),
            content = node.get("content").let { if (it.isObject || it.isArray) it.toString() else it.asText() },
            useSetters = node.path("useSetters").asBoolean(false),
            useGetters = node.path("useGetters").asBoolean(false),
            useStrings = node.path("useStrings").asBoolean(false),
            useKotlinSingleDataClass = node.path("useKotlinSingleDataClass").asBoolean(false),
            useKotlinParcelable = node.path("useKotlinParcelable").asBoolean(false),
            kotlinNullableFields = node.path("kotlinNullableFields").asBoolean(true),
            javaPrimitives = node.path("javaPrimitives").asBoolean(false),
            useTabsIndentation = node.path("useTabsIndentation").asBoolean(false),
            useLombokValue = node.path("useLombokValue").asBoolean(false),
            useMoshiAdapter = node.path("useMoshiAdapter").asBoolean(false),
            useKotlinDataClass = node.path("useKotlinDataClass").asBoolean(true)
        )
    }
}

private val mapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    registerModule(SimpleModule().addDeserializer(GenerationModel::class.java, GenerationModelDeserializer()))
}

fun main(args: Array<String>) {
    val raw = when {
        args.size == 1 && File(args[0]).isFile -> File(args[0]).readText()
        args.isEmpty() -> System.`in`.bufferedReader().readText()
        else -> {
            System.err.println("Usage: robopojo [config.json]  OR  pipe JSON to stdin")
            exitProcess(1)
        }
    }

    val node = mapper.readTree(raw)
    val model = mapper.treeToValue<GenerationModel>(node)
    val dir = File(node.get("output").asText()).apply { mkdirs() }
    val packageName = node.get("package")?.asText()
    GeneratorFacade().generate(model, dir, packageName)
}
