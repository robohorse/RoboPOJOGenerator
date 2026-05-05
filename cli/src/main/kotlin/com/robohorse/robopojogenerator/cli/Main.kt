package com.robohorse.robopojogenerator.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import com.robohorse.robopojogenerator.GeneratorFacade
import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.models.JavaStyle
import java.io.File
import java.io.IOException

class RoboPojoCommand : CliktCommand(name = "robopojo") {

    override fun help(context: Context) = "Generate Java/Kotlin data classes from JSON"

    val contentFile by argument("content", help = "JSON file to generate classes from (or stdin)")
        .file(mustExist = true, canBeDir = false)
        .optional()

    val name by option("-n", "--name", help = "Root class name (inferred from filename if omitted)")
    val outputDir by option("-o", "--output", help = "Output directory")
        .file()
        .default(File("."))
    val packageName by option("-p", "--package", help = "Package declaration")
    val framework by option("--framework", help = "Serialization framework")
        .choice("none", "gson", "jackson", "moshi", "logan-square", "auto-value", "fast-json", "jakarta", "kotlinx")
        .default("none")
    val style by option("--style", help = "Java output style")
        .choice("CLASS", "RECORD", "LOMBOK")
        .default("CLASS")
    val kotlin by option("--kotlin", help = "Generate Kotlin instead of Java").flag()
    val getters by option("--getters", help = "Generate getter methods").flag()
    val setters by option("--setters", help = "Generate setter methods").flag()
    val toStringFlag by option("--to-string", help = "Generate toString()").flag()
    val primitives by option("--primitives", help = "Use Java primitives (int, boolean)").flag()
    val tabs by option("--tabs", help = "Use tab indentation (default: 4 spaces)").flag()
    val lombokValue by option("--lombok-value", help = "Lombok @Value annotation").flag()
    val moshiAdapter by option("--moshi-adapter", help = "Generate Moshi adapter").flag()
    val kotlinNullable by option("--nullable", help = "Nullable Kotlin fields (default: true)").flag("--non-nullable", default = true)
    val kotlinDataClass by option("--data-class", help = "Kotlin data classes (default: true)").flag("--no-data-class", default = true)
    val kotlinSingleFile by option("--single-file", help = "All Kotlin classes in one file").flag()
    val kotlinParcelable by option("--parcelable", help = "Kotlin Parcelable (Android)").flag()

    override fun run() {
        val content = contentFile?.let { file ->
            try {
                file.readText()
            } catch (e: IOException) {
                throw CliktError("Cannot read file '${file.absolutePath}': ${e.message}")
            }
        } ?: run {
            if (System.`in`.available() == 0) {
                System.err.println("Reading JSON from stdin (Ctrl+D to end)...")
            }
            val stdin = System.`in`.bufferedReader().readText()
            if (stdin.isBlank()) {
                throw CliktError("No JSON received on stdin. Provide a file argument or pipe JSON content.")
            }
            stdin
        }

        val className = name
            ?: contentFile?.let { inferClassName(it.name) }?.takeIf { it.isNotBlank() }
            ?: throw CliktError("--name (-n) is required (cannot infer class name from '${contentFile?.name ?: "stdin"}')")

        if (!className.first().isLetter()) {
            throw CliktError(
                "Inferred class name '$className' starts with a non-letter — use -n to specify a valid class name"
            )
        }

        val javaStyle = JavaStyle.valueOf(style)

        val model = GenerationModel(
            rootClassName = className,
            content = content,
            useKotlin = kotlin,
            annotationEnum = FrameworkVW.fromString(framework, style = javaStyle),
            useSetters = setters,
            useGetters = getters,
            useStrings = toStringFlag,
            useKotlinSingleDataClass = kotlinSingleFile,
            useKotlinParcelable = kotlinParcelable,
            kotlinNullableFields = kotlinNullable,
            javaPrimitives = primitives,
            useTabsIndentation = tabs,
            useLombokValue = lombokValue,
            useMoshiAdapter = moshiAdapter,
            useKotlinDataClass = kotlinDataClass
        )

        val generated = try {
            GeneratorFacade().generate(model, outputDir, packageName)
        } catch (e: IllegalArgumentException) {
            throw CliktError("Invalid input: ${e.message}")
        } catch (e: IOException) {
            throw CliktError("File I/O error: ${e.message}")
        } catch (e: Exception) {
            System.err.println("Unexpected error during generation:")
            e.printStackTrace(System.err)
            throw CliktError("Internal error (${e.javaClass.simpleName}): ${e.message ?: "no details"}")
        }

        System.err.println("Generated ${generated.size} file(s) in ${outputDir.absolutePath}")
        generated.forEach { System.err.println("  ${it.name}") }
    }
}

fun inferClassName(filename: String): String {
    val base = filename.substringBeforeLast('.')
    return base.split('-', '_', '.')
        .filter { it.isNotEmpty() }
        .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
        .filter { it.isLetterOrDigit() }
}

fun main(args: Array<String>) = RoboPojoCommand().main(args)
