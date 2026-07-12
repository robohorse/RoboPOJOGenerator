package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.GenerationModel
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GeneratorFacadeTest {

    private val facade = GeneratorFacade()

    private fun baseModel(
        content: String,
        rootClassName: String = "Response",
        annotationEnum: FrameworkVW = FrameworkVW.None(),
        useKotlin: Boolean = false,
        useLombokValue: Boolean = false,
        javaPrimitives: Boolean = false,
        useKotlinSingleDataClass: Boolean = false
    ) = GenerationModel(
        rewriteClasses = true,
        useKotlin = useKotlin,
        annotationEnum = annotationEnum,
        rootClassName = rootClassName,
        content = content,
        useSetters = false,
        useGetters = false,
        useStrings = false,
        useKotlinSingleDataClass = useKotlinSingleDataClass,
        useKotlinParcelable = false,
        kotlinNullableFields = true,
        javaPrimitives = javaPrimitives,
        useTabsIndentation = false,
        useLombokValue = useLombokValue,
        useMoshiAdapter = false,
        useKotlinDataClass = true
    )

    private fun withTempDir(block: (File) -> Unit) {
        val dir = File.createTempFile("robopojo", "test").apply { delete(); mkdirs() }
        try {
            block(dir)
        } finally {
            dir.deleteRecursively()
        }
    }

    @Test
    fun testSimpleFlatJson() = withTempDir { dir ->
        facade.generate(baseModel("""{"name": "foo", "age": 25}"""), dir)
        val file = File(dir, "Response.java")
        assertTrue(file.exists())
        val content = file.readText()
        assertTrue(content.contains("class Response"))
        assertTrue(content.contains("name"))
        assertTrue(content.contains("age"))
    }

    @Test
    fun testNestedJsonProducesMultipleFiles() = withTempDir { dir ->
        facade.generate(baseModel("""{"user": {"name": "foo", "email": "a@b.com"}}"""), dir)
        assertTrue(File(dir, "Response.java").exists())
        assertTrue(File(dir, "User.java").exists())
        assertEquals(2, dir.listFiles()!!.size)
    }

    @Test
    fun testJacksonAnnotations() = withTempDir { dir ->
        facade.generate(
            baseModel("""{"accountId": "abc"}""", annotationEnum = FrameworkVW.Jackson()),
            dir,
            packageName = "com.example"
        )
        val content = File(dir, "Response.java").readText()
        assertTrue(content.contains("@JsonProperty"))
        assertTrue(content.contains("com.fasterxml.jackson.annotation.JsonProperty"))
    }

    @Test
    fun testPackageName() = withTempDir { dir ->
        facade.generate(baseModel("""{"id": 1}"""), dir, packageName = "com.example.dto")
        val content = File(dir, "Response.java").readText()
        assertTrue(content.contains("package com.example.dto;"))
    }

    @Test
    fun testKotlinDataClass() = withTempDir { dir ->
        facade.generate(baseModel("""{"name": "foo"}""", useKotlin = true), dir)
        val content = File(dir, "Response.kt").readText()
        assertTrue(content.contains("data class Response"))
    }

    @Test
    fun testLombokValue() = withTempDir { dir ->
        facade.generate(
            baseModel("""{"active": true}""", annotationEnum = FrameworkVW.NoneLombok(), useLombokValue = true),
            dir,
            packageName = "com.example"
        )
        val content = File(dir, "Response.java").readText()
        assertTrue(content.contains("@Value"))
        assertTrue(content.contains("lombok.Value"))
    }

    @Test
    fun testKotlinSingleFile() = withTempDir { dir ->
        facade.generate(
            baseModel("""{"user": {"name": "foo"}}""", useKotlin = true, useKotlinSingleDataClass = true),
            dir
        )
        val files = dir.listFiles()!!
        assertEquals(1, files.size)
        assertEquals("Response.kt", files[0].name)
        val content = files[0].readText()
        assertTrue(content.contains("data class Response"))
        assertTrue(content.contains("data class User"))
    }

    @Test
    fun testJsonArrayInput() = withTempDir { dir ->
        facade.generate(baseModel("""[{"id": 1, "name": "Alice"}, {"id": 2}]"""), dir)
        assertTrue(File(dir, "Response.java").exists())
    }

    @Test
    fun testMalformedJsonThrows() = withTempDir { dir ->
        assertFailsWith<IllegalArgumentException> {
            facade.generate(baseModel("not json at all"), dir)
        }
    }

    @Test
    fun testBlankContentThrows() {
        assertFailsWith<IllegalArgumentException> {
            baseModel("   ")
        }
    }

    @Test
    fun testBlankClassNameThrows() {
        assertFailsWith<IllegalArgumentException> {
            GenerationModel(rootClassName = "  ", content = """{"id": 1}""")
        }
    }

    @Test
    fun testOutputDirIsFileThrows() = withTempDir { dir ->
        val file = File(dir, "notADir.txt").apply { writeText("hello") }
        assertFailsWith<IllegalArgumentException> {
            facade.generate(baseModel("""{"id": 1}"""), file)
        }
    }

    @Test
    fun testOutputDirAutoCreated() {
        val dir = File(File.createTempFile("robopojo", "test").apply { delete() }, "nested/output")
        try {
            facade.generate(baseModel("""{"id": 1}"""), dir)
            assertTrue(dir.isDirectory)
            assertTrue(File(dir, "Response.java").exists())
        } finally {
            dir.parentFile.parentFile.deleteRecursively()
        }
    }
}
