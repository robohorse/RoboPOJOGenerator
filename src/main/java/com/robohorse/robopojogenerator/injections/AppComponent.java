package com.robohorse.robopojogenerator.injections;

import com.robohorse.robopojogenerator.GeneratePOJOAction;
import com.robohorse.robopojogenerator.action.GenerateActionListener;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by vadim on 28.09.16.
 */
@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {

    void inject(GeneratePOJOAction item);

    void inject(GenerateActionListener item);

}
