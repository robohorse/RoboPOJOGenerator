package com.robohorse.robopojogenerator.generator.const.templates

import com.robohorse.robopojogenerator.generator.consts.templates.*
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate.*
import com.robohorse.robopojogenerator.generator.consts.templates.ImportsTemplate.Companion.SERIALIZED_NAME
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ImportsTemplateTest {
    @Test
    fun check_GSON() {
        assertEquals(GSON.imports[0], SERIALIZED_NAME)
    }

    @Test
    fun check_LOGAN_SQUARE() {
        assertEquals(LOGAN_SQUARE.imports[0], SERIALIZED_NAME)
        assertEquals(LOGAN_SQUARE.imports[1], JSON_OBJECT)
        assertEquals(LOGAN_SQUARE.imports[2], JSON_FIELD)
    }

    @Test
    fun check_JACKSON() {
        assertEquals(JACKSON.imports[0], JSON_PROPERTY)
    }

    @Test
    fun check_AUTO_VALUE_GSON() {
        assertEquals(AUTO_VALUE_GSON.imports[0], SERIALIZED_NAME)
        assertEquals(AUTO_VALUE_GSON.imports[1], AUTO_VALUE)
        assertEquals(AUTO_VALUE_GSON.imports[2], TYPED_ADAPTER)
        assertEquals(AUTO_VALUE_GSON.imports[3], GSON_IMPORT)
    }

    @Test
    fun check_FAST_JSON() {
        assertEquals(FAST_JSON.imports[0], FAST_JSON_PROPERTY)
    }

    @Test
    fun check_MOSHI() {
        assertEquals(MOSHI.imports[0], MOSHI_PROPERTY)
    }
}
