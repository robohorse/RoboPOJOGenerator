package com.robohorse.robopojogenerator.errors

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RoboPluginExceptionTest {
    @Test
    fun check_FileWriteException() {
        val message = "Something was wrong..."
        val exception = FileWriteException(message)
        assertEquals(message, exception.message)
        assertEquals("File creation exception:", exception.header)
    }

    @Test
    fun check_JSONStructureException() {
        val exception = JSONStructureException()
        assertEquals("incorrect structure", exception.message)
        assertEquals("JSON exception:", exception.header)
    }

    @Test
    fun check_PathException() {
        val exception = PathException()
        assertEquals(
                "You should choose directory for POJO files, before call RoboPOJOGenerator",
                exception.message
        )
        assertEquals("No directory was selected:", exception.header)
    }

    @Test
    fun check_WrongClassNameException() {
        val exception = WrongClassNameException()
        assertEquals("you should set root class name", exception.message)
        assertEquals("Wrong class name:", exception.header)
    }
}
