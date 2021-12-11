package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.controllers.GeneratePOJOActionController
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
}
