package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.properties.ClassItem
import com.robohorse.robopojogenerator.processing.ClassProcessor
import com.robohorse.robopojogenerator.utils.ProcessingModelResolver
import com.robohorse.robopojogenerator.models.GenerationModel

internal class RoboPOJOGenerator(
    private val processor: ClassProcessor,
    private val processingModelResolver: ProcessingModelResolver
) {

    fun generate(model: GenerationModel): Set<ClassItem> {
        val map = LinkedHashMap<String?, ClassItem>()
        processor.proceed(
            processingModelResolver.resolveJsonModel(model),
            map
        )
        return HashSet(map.values)
    }
}
