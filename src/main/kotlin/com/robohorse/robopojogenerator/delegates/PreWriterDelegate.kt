package com.robohorse.robopojogenerator.delegates

import com.robohorse.robopojogenerator.models.GenerationModel

class PreWriterDelegate(
        private val indentationDelegate: IndentationDelegate
) {

    fun updateFileBody(
            generationModel: GenerationModel, body: String
    ) = if (!generationModel.useTabsIndentation) {
        indentationDelegate.updateFileBody(body)
    } else {
        body
    }
}
