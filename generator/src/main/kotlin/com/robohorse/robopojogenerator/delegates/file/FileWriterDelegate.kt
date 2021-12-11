package com.robohorse.robopojogenerator.delegates.file

import org.apache.commons.io.FileUtils
import java.io.File

internal class FileWriterDelegate {

    fun writeToFile(
        classItemBody: String,
        file: File
    ) {
        if (!file.exists()) {
            file.createNewFile()
            FileUtils.writeStringToFile(
                file,
                classItemBody,
                ENCODING
            )
        }
    }
}

private const val ENCODING = "UTF-8"
