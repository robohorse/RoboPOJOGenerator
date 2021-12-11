package com.robohorse.robopojogenerator

import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegateImpl
import com.robohorse.robopojogenerator.delegates.IndentationDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegate
import com.robohorse.robopojogenerator.delegates.MessageDelegateImpl
import com.robohorse.robopojogenerator.delegates.PreWriterDelegate
import com.robohorse.robopojogenerator.delegates.PreWriterDelegateImpl
import org.koin.dsl.module

val coreModule = module {
    single<EnvironmentDelegate> {
        EnvironmentDelegateImpl()
    }

    single<MessageDelegate> {
        MessageDelegateImpl()
    }

    single<PreWriterDelegate> {
        PreWriterDelegateImpl(get())
    }

    single {
        IndentationDelegate()
    }
}
