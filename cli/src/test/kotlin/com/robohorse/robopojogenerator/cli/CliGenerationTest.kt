package com.robohorse.robopojogenerator.cli

import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.parse
import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CliGenerationTest {

    private fun withTempDir(block: (File) -> Unit) {
        val dir = File.createTempFile("cli-test", "dir").apply { delete(); mkdirs() }
        try {
            block(dir)
        } finally {
            dir.deleteRecursively()
        }
    }

    private fun contentFile(json: String, dir: File, name: String = "input.json"): File {
        val inputDir = File(dir, "input").apply { mkdirs() }
        return File(inputDir, name).apply { writeText(json) }
    }

    private fun run(vararg args: String) {
        RoboPojoCommand().main(args.toList())
    }

    private val sampleJson = """{"accountId": "abc", "active": true}"""
    private val nestedJson = """{"address": {"street": "123 Main", "city": "NYC"}}"""

    @Test
    fun testJavaPlain() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("class Account"))
        assertTrue(content.contains("accountId"))
        assertTrue(content.contains("active"))
    }

    @Test
    fun testInferClassName() {
        assertEquals("UserProfile", inferClassName("user-profile.json"))
        assertEquals("UserProfile", inferClassName("user_profile.json"))
        assertEquals("Response", inferClassName("response.json"))
        assertEquals("MyData", inferClassName("my.data.json"))
    }

    @Test
    fun testExplicitName() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir)
        run("-n", "MyModel", "-o", dir.absolutePath, f.absolutePath)
        assertTrue(File(dir, "MyModel.java").exists())
    }

    @Test
    fun testWithPackage() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "-p", "com.example.dto", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("package com.example.dto;"))
    }

    @Test
    fun testJackson() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--framework", "jackson", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@JsonProperty"))
    }

    @Test
    fun testGson() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--framework", "gson", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@SerializedName"))
    }

    @Test
    fun testRecords() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--style", "RECORD", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("record Account"))
    }

    @Test
    fun testLombokValue() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--style", "LOMBOK", "--lombok-value", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@Value"))
    }

    @Test
    fun testKotlinFlag() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--kotlin", f.absolutePath)
        val content = File(dir, "Account.kt").readText()
        assertTrue(content.contains("data class Account"))
    }

    @Test
    fun testKotlinSingleFile() = withTempDir { dir ->
        val f = contentFile(nestedJson, dir, "account.json")
        run("-o", dir.absolutePath, "--kotlin", "--single-file", f.absolutePath)
        val files = dir.listFiles()!!.filter { it.name != "input" }
        assertEquals(1, files.size)
        val content = files[0].readText()
        assertTrue(content.contains("data class Account"))
        assertTrue(content.contains("data class Address"))
    }

    @Test
    fun testNestedObjects() = withTempDir { dir ->
        val f = contentFile(nestedJson, dir, "account.json")
        run("-o", dir.absolutePath, f.absolutePath)
        assertTrue(File(dir, "Account.java").exists())
        assertTrue(File(dir, "Address.java").exists())
    }

    @Test
    fun testTabIndentation() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--tabs", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("\t"))
    }

    @Test
    fun testSpaceIndentationDefault() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(!content.contains("\t"))
    }

    @Test
    fun testGettersSetters() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "account.json")
        run("-o", dir.absolutePath, "--getters", "--setters", f.absolutePath)
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("getAccountId"))
        assertTrue(content.contains("setAccountId"))
    }

    @Test
    fun testJsonArrayInput() = withTempDir { dir ->
        val f = contentFile("""[{"id": 1, "name": "Alice"}]""", dir, "users.json")
        run("-o", dir.absolutePath, f.absolutePath)
        assertTrue(File(dir, "Users.java").exists())
    }

    @Test
    fun testMalformedJsonError() = withTempDir { dir ->
        val f = contentFile("this is not json", dir, "bad.json")
        assertFailsWith<CliktError> {
            RoboPojoCommand().parse(listOf("-o", dir.absolutePath, f.absolutePath))
        }
    }

    @Test
    fun testInferClassNameEdgeCases() {
        assertEquals("", inferClassName(".json"))
        assertEquals("", inferClassName("----.json"))
        assertEquals("Data", inferClassName("data"))
        assertEquals("123Data", inferClassName("123-data.json"))
    }

    @Test
    fun testEmptyInferredNameRequiresExplicitFlag() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, ".json")
        assertFailsWith<CliktError> {
            RoboPojoCommand().parse(listOf("-o", dir.absolutePath, f.absolutePath))
        }
    }

    @Test
    fun testDigitPrefixInferredNameRequiresExplicitFlag() = withTempDir { dir ->
        val f = contentFile(sampleJson, dir, "123-data.json")
        assertFailsWith<CliktError> {
            RoboPojoCommand().parse(listOf("-o", dir.absolutePath, f.absolutePath))
        }
    }

    @Test
    fun testStdinWithNoNameFails() {
        val originalIn = System.`in`
        try {
            System.setIn(sampleJson.byteInputStream())
            assertFailsWith<CliktError> {
                RoboPojoCommand().parse(listOf("-o", "."))
            }
        } finally {
            System.setIn(originalIn)
        }
    }

    @Test
    fun testStdinWithNameSucceeds() = withTempDir { dir ->
        val originalIn = System.`in`
        try {
            System.setIn(sampleJson.byteInputStream())
            RoboPojoCommand().parse(listOf("-n", "StdinModel", "-o", dir.absolutePath))
            assertTrue(File(dir, "StdinModel.java").exists())
        } finally {
            System.setIn(originalIn)
        }
    }

    @Test
    fun testBlankStdinFails() {
        val originalIn = System.`in`
        try {
            System.setIn("   ".byteInputStream())
            assertFailsWith<CliktError> {
                RoboPojoCommand().parse(listOf("-n", "Foo", "-o", "."))
            }
        } finally {
            System.setIn(originalIn)
        }
    }

    @Test
    fun testFieldCountGuard() {
        val fieldCount = GenerationModel::class.java.declaredFields
            .count { !it.isSynthetic && it.name != "Companion" }
        assertEquals(16, fieldCount, "GenerationModel gained a new field — update allFieldsCovered() and CLI flags")
    }
}

/**
 * Compile-time coverage: if a field is added to GenerationModel without a default,
 * this fails to compile — forcing the CLI to be updated.
 */
@Suppress("unused")
private fun allFieldsCovered() = GenerationModel(
    rootClassName = "X",
    content = "{}",
    rewriteClasses = true,
    useKotlin = false,
    annotationEnum = FrameworkVW.None(),
    useSetters = false,
    useGetters = false,
    useStrings = false,
    useKotlinSingleDataClass = false,
    useKotlinParcelable = false,
    kotlinNullableFields = true,
    javaPrimitives = false,
    useTabsIndentation = false,
    useLombokValue = false,
    useMoshiAdapter = false,
    useKotlinDataClass = true
)
