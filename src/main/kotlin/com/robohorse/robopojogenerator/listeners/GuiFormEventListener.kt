package com.robohorse.robopojogenerator.listeners

import com.robohorse.robopojogenerator.models.GenerationModel

interface GuiFormEventListener {
    fun onJsonDataObtained(model: GenerationModel)
}
