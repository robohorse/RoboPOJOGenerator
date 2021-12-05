package com.robohorse.robopojogenerator.delegates

import kotlin.test.Test
import kotlin.test.assertEquals

class IndentationDelegateTest {
    private val indentationDelegate = IndentationDelegate()

    @Test
    fun check_body_update() {
        assertEquals(RESULT, indentationDelegate.updateFileBody(SOURCE))
    }
}

private const val SOURCE = "class MyClass{\t}"
private const val RESULT = "class MyClass{    }"
