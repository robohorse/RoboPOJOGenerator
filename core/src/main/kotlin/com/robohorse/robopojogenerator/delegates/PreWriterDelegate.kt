package com.robohorse.robopojogenerator.delegates

import com.robohorse.robopojogenerator.models.GenerationModel

interface PreWriterDelegate {

    fun updateFileBody(
        generationModel: GenerationModel,
        body: String
    ): String
}

internal class PreWriterDelegateImpl(
    private val indentationDelegate: IndentationDelegate
) : PreWriterDelegate {

    override fun updateFileBody(
        generationModel: GenerationModel,
        body: String
    ) = if (!generationModel.useTabsIndentation) {
        indentationDelegate.updateFileBody(body)
    } else {
        body
    }
}
