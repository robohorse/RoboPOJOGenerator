package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.models.GenerationModel

internal interface GuiFormEventListener {
    fun onJsonDataObtained(model: GenerationModel)
}
