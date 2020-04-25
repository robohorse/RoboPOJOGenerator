package com.robohorse.robopojogenerator.di

import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate
import com.robohorse.robopojogenerator.delegates.file.CommonFileWriterDelegate
import com.robohorse.robopojogenerator.delegates.GenerationDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.file.FileWriterDelegate
import com.robohorse.robopojogenerator.delegates.file.KotlinFileWriterDelegate
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator
import com.robohorse.robopojogenerator.generator.consts.common.ClassCreator
import com.robohorse.robopojogenerator.generator.consts.common.FileWriteFactory
import com.robohorse.robopojogenerator.generator.postrocessing.PostProcessorFactory
import com.robohorse.robopojogenerator.generator.postrocessing.common.AutoValueClassPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.CommonJavaPostProcessor
import com.robohorse.robopojogenerator.generator.postrocessing.common.KotlinDataClassPostProcessor
import com.robohorse.robopojogenerator.generator.processing.ClassProcessor
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper
import com.robohorse.robopojogenerator.generator.utils.ClassTemplateHelper
import com.robohorse.robopojogenerator.view.GeneratorViewBinder
import com.robohorse.robopojogenerator.view.GeneratorViewFactory
import com.robohorse.robopojogenerator.view.PropertiesFactory
import com.robohorse.robopojogenerator.view.ViewModelMapper
import org.koin.dsl.module

val appModule = module {
    single {
        GeneratePOJOActionController(get(), get(), get(), get())
    }

    single {
        EnvironmentDelegate()
    }

    single {
        MessageDelegate()
    }

    single {
        GeneratorViewFactory(get(), get(), get(), get())
    }

    single {
        ViewModelMapper(get())
    }

    single {
        GeneratorViewBinder(get())
    }

    single {
        PropertiesFactory()
    }

    single {
        GenerationDelegate(get(), get(), get())
    }

    single {
        ClassCreator(get(), get())
    }

    single {
        FileWriteFactory(get(), get())
    }

    single {
        KotlinFileWriterDelegate(get(), get(), get(), get())
    }

    single {
        FileWriterDelegate()
    }

    single {
        CommonFileWriterDelegate(get(), get(), get())
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
        RoboPOJOGenerator(get())
    }

    single {
        ClassProcessor(get())
    }

    single { ClassGenerateHelper() }
}