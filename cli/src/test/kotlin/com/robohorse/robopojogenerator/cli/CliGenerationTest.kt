package com.robohorse.robopojogenerator.cli

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CliGenerationTest {

    private val mapper = jacksonObjectMapper()

    private fun withTempDir(block: (File) -> Unit) {
        val dir = File.createTempFile("cli-test", "dir").apply { delete(); mkdirs() }
        try {
            block(dir)
        } finally {
            dir.deleteRecursively()
        }
    }

    private fun run(config: ObjectNode) {
        val file = File.createTempFile("robopojo-config", ".json").apply { writeText(config.toString()) }
        try {
            main(arrayOf(file.absolutePath))
        } finally {
            file.delete()
        }
    }

    private fun config(outputDir: File, extras: Map<String, Any> = emptyMap()): ObjectNode {
        val obj = mapper.createObjectNode()
        obj.put("rootClassName", "Account")
        obj.put("output", outputDir.absolutePath)
        obj.set<ObjectNode>("content", mapper.createObjectNode().put("accountId", "abc").put("active", true))
        extras.forEach { (k, v) ->
            when (v) {
                is Boolean -> obj.put(k, v)
                is String -> obj.put(k, v)
                is Int -> obj.put(k, v)
                else -> obj.put(k, v.toString())
            }
        }
        return obj
    }

    @Test
    fun testJavaPlain() = withTempDir { dir ->
        run(config(dir))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("class Account"))
        assertTrue(content.contains("accountId"))
        assertTrue(content.contains("active"))
    }

    @Test
    fun testWithPackage() = withTempDir { dir ->
        run(config(dir, mapOf("package" to "com.example.dto")))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("package com.example.dto;"))
    }

    @Test
    fun testJackson() = withTempDir { dir ->
        run(config(dir, mapOf("framework" to "jackson", "package" to "com.example")))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@JsonProperty"))
    }

    @Test
    fun testGson() = withTempDir { dir ->
        run(config(dir, mapOf("framework" to "gson", "package" to "com.example")))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@SerializedName"))
    }

    @Test
    fun testRecords() = withTempDir { dir ->
        run(config(dir, mapOf("javaStyle" to "RECORD", "package" to "com.example")))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("record Account"))
    }

    @Test
    fun testLombokValue() = withTempDir { dir ->
        run(config(dir, mapOf("javaStyle" to "LOMBOK", "useLombokValue" to true, "package" to "com.example")))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("@Value"))
    }

    @Test
    fun testKotlinDataClass() = withTempDir { dir ->
        run(config(dir, mapOf("useKotlin" to true)))
        val content = File(dir, "Account.kt").readText()
        assertTrue(content.contains("data class Account"))
    }

    @Test
    fun testKotlinSingleFile() = withTempDir { dir ->
        val cfg = config(dir, mapOf("useKotlin" to true, "useKotlinSingleDataClass" to true))
        cfg.set<ObjectNode>("content", mapper.createObjectNode().set("user", mapper.createObjectNode().put("name", "foo").put("age", 25)))
        run(cfg)
        val files = dir.listFiles()!!
        assertEquals(1, files.size)
        val content = files[0].readText()
        assertTrue(content.contains("data class Account"))
        assertTrue(content.contains("data class User"))
    }

    @Test
    fun testNestedObjects() = withTempDir { dir ->
        val cfg = config(dir)
        cfg.set<ObjectNode>("content", mapper.createObjectNode().set("address", mapper.createObjectNode().put("street", "123 Main").put("city", "NYC")))
        run(cfg)
        assertTrue(File(dir, "Account.java").exists())
        assertTrue(File(dir, "Address.java").exists())
    }

    @Test
    fun testTabIndentation() = withTempDir { dir ->
        run(config(dir, mapOf("useTabsIndentation" to true)))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("\t"))
    }

    @Test
    fun testSpaceIndentationDefault() = withTempDir { dir ->
        run(config(dir))
        val content = File(dir, "Account.java").readText()
        assertTrue(!content.contains("\t"))
    }

    @Test
    fun testGettersSetters() = withTempDir { dir ->
        run(config(dir, mapOf("useGetters" to true, "useSetters" to true)))
        val content = File(dir, "Account.java").readText()
        assertTrue(content.contains("getAccountId"))
        assertTrue(content.contains("setAccountId"))
    }
}
