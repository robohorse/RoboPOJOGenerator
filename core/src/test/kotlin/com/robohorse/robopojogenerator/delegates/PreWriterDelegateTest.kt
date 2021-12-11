package com.robohorse.robopojogenerator.delegates

import com.robohorse.robopojogenerator.models.GenerationModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PreWriterDelegateTest {
    @RelaxedMockK
    lateinit var indentationDelegate: IndentationDelegate

    @RelaxedMockK
    lateinit var generationModel: GenerationModel

    @InjectMockKs
    lateinit var preWriterDelegate: PreWriterDelegateImpl

    @BeforeTest
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun checkIndentationApply() {
        every { generationModel.useTabsIndentation }.returns(false)
        every { indentationDelegate.updateFileBody(SOURCE) }.returns(RESULT)
        assertEquals(RESULT, preWriterDelegate.updateFileBody(generationModel, SOURCE))
    }

    @Test
    fun checkIndentationSkip() {
        every { generationModel.useTabsIndentation }.returns(true)
        every { indentationDelegate.updateFileBody(SOURCE) }.returns(RESULT)
        assertEquals(SOURCE, preWriterDelegate.updateFileBody(generationModel, SOURCE))
    }
}

private const val SOURCE = "class MyClass{\t}"
private const val RESULT = "class MyClass{    }"
