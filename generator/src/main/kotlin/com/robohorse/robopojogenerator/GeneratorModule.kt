package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.filewriter.FileDelegateFactory
import com.robohorse.robopojogenerator.filewriter.FileWriter
import com.robohorse.robopojogenerator.filewriter.common.CommonFileWriterDelegate
import com.robohorse.robopojogenerator.filewriter.common.KotlinSingleFileWriterDelegate
import com.robohorse.robopojogenerator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.processing.ClassCreator
import com.robohorse.robopojogenerator.processing.ClassProcessor
import com.robohorse.robopojogenerator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.utils.ProcessingModelResolver
import org.koin.dsl.module

val generatorModule = module {

    single {
        ClassCreator(get(), get())
    }

    single {
        FileDelegateFactory(get(), get())
    }

    single {
        KotlinSingleFileWriterDelegate(get(), get(), get(), get(), get())
    }

    single<GenerationDelegate> {
        GenerationDelegateImpl(get(), get(), get())
    }

    single {
        FileWriter()
    }

    single {
        CommonFileWriterDelegate(get(), get(), get(), get())
    }

    single {
        PostProcessorFactory(get(), get(), get())
    }

    single {
        KotlinDataClassPostProcessor(get(), get())
    }

    single {
        AutoValueClassPostProcessor(get(), get())
    }

    single {
        CommonJavaPostProcessor(get(), get())
    }

    single {
        ClassTemplateHelper(get())
    }

    single {
        RoboPOJOGenerator(get(), get())
    }

    single {
        ProcessingModelResolver()
    }

    single {
        ClassProcessor(get())
    }

    single { ClassGenerateHelper() }
}
