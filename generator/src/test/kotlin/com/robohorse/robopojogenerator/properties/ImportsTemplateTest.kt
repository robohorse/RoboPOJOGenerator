package com.robohorse.robopojogenerator.properties

import com.robohorse.robopojogenerator.properties.templates.AUTO_VALUE
import com.robohorse.robopojogenerator.properties.templates.FAST_JSON_PROPERTY
import com.robohorse.robopojogenerator.properties.templates.GSON_IMPORT
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.AUTO_VALUE_GSON
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.Companion.SERIALIZED_NAME
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.FAST_JSON
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.GSON
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.JACKSON
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.LOGAN_SQUARE
import com.robohorse.robopojogenerator.properties.templates.ImportsTemplate.MOSHI
import com.robohorse.robopojogenerator.properties.templates.JSON_FIELD
import com.robohorse.robopojogenerator.properties.templates.JSON_OBJECT
import com.robohorse.robopojogenerator.properties.templates.JSON_PROPERTY
import com.robohorse.robopojogenerator.properties.templates.MOSHI_PROPERTY
import com.robohorse.robopojogenerator.properties.templates.TYPED_ADAPTER
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ImportsTemplateTest {
    @Test
    fun check_GSON() {
        assertEquals(GSON.imports[0], SERIALIZED_NAME)
    }

    @Test
    fun check_LOGAN_SQUARE() {
        assertEquals(LOGAN_SQUARE.imports[0], JSON_OBJECT)
        assertEquals(LOGAN_SQUARE.imports[1], JSON_FIELD)
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
        assertEquals(MOSHI().imports[0], MOSHI_PROPERTY)
    }
}
