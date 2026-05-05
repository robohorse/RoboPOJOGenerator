package com.robohorse.robopojogenerator.cli

import com.robohorse.robopojogenerator.models.FrameworkVW
import com.robohorse.robopojogenerator.models.JavaStyle
import kotlin.test.Test
import kotlin.test.assertIs

class ArgParsingTest {

    @Test
    fun testFrameworkNone() {
        assertIs<FrameworkVW.None>(FrameworkVW.fromString("none"))
    }

    @Test
    fun testFrameworkRecords() {
        assertIs<FrameworkVW.NoneJavaRecords>(FrameworkVW.fromString("none", JavaStyle.RECORD))
    }

    @Test
    fun testFrameworkLombok() {
        assertIs<FrameworkVW.NoneLombok>(FrameworkVW.fromString("none", JavaStyle.LOMBOK))
    }

    @Test
    fun testFrameworkGson() {
        assertIs<FrameworkVW.Gson>(FrameworkVW.fromString("gson"))
    }

    @Test
    fun testFrameworkGsonRecords() {
        assertIs<FrameworkVW.GsonJavaRecords>(FrameworkVW.fromString("gson", JavaStyle.RECORD))
    }

    @Test
    fun testFrameworkJackson() {
        assertIs<FrameworkVW.Jackson>(FrameworkVW.fromString("jackson"))
    }

    @Test
    fun testFrameworkJacksonRecords() {
        assertIs<FrameworkVW.JacksonJavaRecords>(FrameworkVW.fromString("jackson", JavaStyle.RECORD))
    }

    @Test
    fun testFrameworkMoshi() {
        assertIs<FrameworkVW.Moshi>(FrameworkVW.fromString("moshi"))
    }

    @Test
    fun testFrameworkMoshiRecords() {
        assertIs<FrameworkVW.MoshiJavaRecords>(FrameworkVW.fromString("moshi", JavaStyle.RECORD))
    }

    @Test
    fun testFrameworkLoganSquare() {
        assertIs<FrameworkVW.LoganSquare>(FrameworkVW.fromString("logan-square"))
    }

    @Test
    fun testFrameworkAutoValue() {
        assertIs<FrameworkVW.AutoValue>(FrameworkVW.fromString("auto-value"))
    }

    @Test
    fun testFrameworkFastJson() {
        assertIs<FrameworkVW.FastJson>(FrameworkVW.fromString("fast-json"))
    }

    @Test
    fun testFrameworkJakarta() {
        assertIs<FrameworkVW.Jakatra>(FrameworkVW.fromString("jakarta"))
    }

    @Test
    fun testFrameworkKotlinX() {
        assertIs<FrameworkVW.KotlinX>(FrameworkVW.fromString("kotlinx"))
    }

    @Test
    fun testCaseInsensitive() {
        assertIs<FrameworkVW.Jackson>(FrameworkVW.fromString("JACKSON"))
    }
}
