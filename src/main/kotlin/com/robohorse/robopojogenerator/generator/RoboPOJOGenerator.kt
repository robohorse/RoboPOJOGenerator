package com.robohorse.robopojogenerator.generator

import com.robohorse.robopojogenerator.generator.consts.common.ClassItem
import com.robohorse.robopojogenerator.generator.processing.ClassProcessor
import com.robohorse.robopojogenerator.generator.utils.ProcessingModelManager
import com.robohorse.robopojogenerator.models.GenerationModel
import java.util.*

class RoboPOJOGenerator(
        private val processor: ClassProcessor,
        private val processingModelManager: ProcessingModelManager
) {

    fun generate(model: GenerationModel): Set<ClassItem> {
        val map = HashMap<String, ClassItem>()
        processor.proceed(
                processingModelManager.resolveJsonModel(model),
                map
        )
        return HashSet(map.values)
    }
}
