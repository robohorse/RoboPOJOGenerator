package com.robohorse.robopojogenerator.utils

import com.robohorse.robopojogenerator.models.GenerationModel
import com.robohorse.robopojogenerator.properties.JsonModel
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class ProcessingModelResolverTest {

    private val resolver = ProcessingModelResolver()

    private fun model(content: String) = GenerationModel(rootClassName = "Root", content = content)

    @Test
    fun jsonObjectInput() {
        val result = resolver.resolveJsonModel(model("""{"id": 1, "name": "Alice"}"""))
        assertIs<JsonModel.JsonItem>(result)
    }

    @Test
    fun jsonArrayInput() {
        val result = resolver.resolveJsonModel(model("""[{"id": 1}, {"id": 2}]"""))
        assertIs<JsonModel.JsonItemArray>(result)
    }

    @Test
    fun invalidContentThrows() {
        assertFailsWith<IllegalArgumentException> {
            resolver.resolveJsonModel(model("not json at all"))
        }
    }

    @Test
    fun whitespaceAroundJsonObject() {
        val result = resolver.resolveJsonModel(model("  \n  {\"id\": 1}  \n  "))
        assertIs<JsonModel.JsonItem>(result)
    }

    @Test
    fun whitespaceAroundJsonArray() {
        val result = resolver.resolveJsonModel(model("  \n  [{\"id\": 1}]  \n  "))
        assertIs<JsonModel.JsonItemArray>(result)
    }

    @Test
    fun emptyJsonObject() {
        val result = resolver.resolveJsonModel(model("{}"))
        assertIs<JsonModel.JsonItem>(result)
    }

    @Test
    fun emptyJsonArray() {
        val result = resolver.resolveJsonModel(model("[]"))
        assertIs<JsonModel.JsonItemArray>(result)
    }

    @Test
    fun malformedJsonObjectThrowsIllegalArgument() {
        assertFailsWith<IllegalArgumentException> {
            resolver.resolveJsonModel(model("{not valid json}"))
        }
    }

    @Test
    fun malformedJsonArrayThrowsIllegalArgument() {
        assertFailsWith<IllegalArgumentException> {
            resolver.resolveJsonModel(model("[broken"))
        }
    }
}
