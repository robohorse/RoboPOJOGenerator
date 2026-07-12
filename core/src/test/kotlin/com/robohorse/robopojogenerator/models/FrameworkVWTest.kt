package com.robohorse.robopojogenerator.models

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FrameworkVWTest {

    private val allInputCombinations: List<FrameworkVW> = buildList {
        val frameworks = listOf("none", "gson", "jackson", "moshi", "logan-square", "auto-value", "fast-json", "jakarta", "kotlinx")
        for (fw in frameworks) {
            for (style in JavaStyle.entries) {
                add(FrameworkVW.fromString(fw, style))
            }
        }
    }

    @Test
    fun fromStringCoversAllSealedSubclasses() {
        val producedClasses = allInputCombinations.map { it::class }.toSet()
        val allSubclasses = FrameworkVW::class.sealedSubclasses.toSet()
        val missing = allSubclasses - producedClasses
        assertTrue(missing.isEmpty(), "fromString does not produce: ${missing.map { it.simpleName }}")
    }

    @Test
    fun fromStringThrowsOnUnknown() {
        assertFailsWith<IllegalArgumentException> {
            FrameworkVW.fromString("bogus")
        }
    }

    @Test
    fun fromStringIsCaseInsensitive() {
        assertTrue(FrameworkVW.fromString("JACKSON") is FrameworkVW.Jackson)
        assertTrue(FrameworkVW.fromString("Gson") is FrameworkVW.Gson)
        assertTrue(FrameworkVW.fromString("KOTLINX") is FrameworkVW.KotlinX)
    }
}
