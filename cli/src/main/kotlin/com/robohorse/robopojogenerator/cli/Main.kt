package com.robohorse.robopojogenerator.cli

import com.fasterxml.jackson.core.JsonParseException
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
import java.io.IOException
import kotlin.system.exitProcess

class GenerationModelDeserializer : JsonDeserializer<GenerationModel>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): GenerationModel {
        val node = p.codec.readTree<JsonNode>(p)

        val styleName = node.path("javaStyle").asText("CLASS").uppercase()
        val style = try {
            JavaStyle.valueOf(styleName)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException(
                "Invalid javaStyle '$styleName'. Valid values: ${JavaStyle.entries.joinToString()}"
            )
        }

        val rootClassName = node.get("rootClassName")?.asText()
            ?: throw IllegalArgumentException("Required field 'rootClassName' is missing from config")

        val contentNode = node.get("content")
            ?: throw IllegalArgumentException("Required field 'content' is missing from config")
        val content = if (contentNode.isObject || contentNode.isArray) contentNode.toString() else contentNode.asText()

        return GenerationModel(
            rewriteClasses = true,
            useKotlin = node.path("useKotlin").asBoolean(false),
            annotationEnum = FrameworkVW.fromString(
                node.path("framework").asText("none"),
                style = style
            ),
            rootClassName = rootClassName,
            content = content,
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
    try {
        val raw = when {
            args.size == 1 -> {
                val file = File(args[0])
                when {
                    !file.exists() -> {
                        System.err.println("Error: config file not found: ${file.absolutePath}")
                        exitProcess(1)
                    }
                    !file.isFile -> {
                        System.err.println("Error: path is not a file: ${file.absolutePath}")
                        exitProcess(1)
                    }
                    else -> file.readText()
                }
            }
            args.isEmpty() -> {
                if (System.console() != null) {
                    System.err.println("Reading JSON config from stdin (Ctrl+D to end, or pass a file path argument)...")
                }
                System.`in`.bufferedReader().readText()
            }
            else -> {
                System.err.println("Usage: robopojo [config.json]  OR  pipe JSON to stdin")
                exitProcess(1)
            }
        }

        val node = mapper.readTree(raw)
        val model = mapper.treeToValue<GenerationModel>(node)

        val outputPath = node.get("output")?.asText()
            ?: throw IllegalArgumentException("Required field 'output' is missing from config")
        val dir = File(outputPath)
        val packageName = node.get("package")?.asText()

        GeneratorFacade().generate(model, dir, packageName)

        val generated = dir.listFiles()?.filter { it.isFile } ?: emptyList()
        System.err.println("Generated ${generated.size} file(s) in ${dir.absolutePath}")
        generated.forEach { System.err.println("  ${it.name}") }
    } catch (e: JsonParseException) {
        System.err.println("Error: invalid JSON: ${e.originalMessage}")
        exitProcess(2)
    } catch (e: IllegalArgumentException) {
        System.err.println("Error: ${e.message}")
        exitProcess(2)
    } catch (e: IOException) {
        System.err.println("Error: ${e.message}")
        exitProcess(2)
    } catch (e: Exception) {
        System.err.println("Error: generation failed: ${e.message}")
        exitProcess(2)
    }
}
