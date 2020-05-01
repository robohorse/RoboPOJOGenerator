package com.robohorse.robopojogenerator.delegates

import com.robohorse.robopojogenerator.generator.consts.templates.ClassTemplate

class IndentationDelegate {

    fun updateFileBody(body: String) =
            body.replace(ClassTemplate.TAB, "$SPACE$SPACE$SPACE$SPACE")
}

private const val SPACE = " "
