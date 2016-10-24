package com.robohorse.robopojogenerator.controllers;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import com.robohorse.robopojogenerator.delegates.EnvironmentDelegate;
import com.robohorse.robopojogenerator.delegates.GenerationDelegate;
import com.robohorse.robopojogenerator.delegates.MessageDelegate;
import com.robohorse.robopojogenerator.view.binders.GeneratorViewBinder;

import javax.inject.Inject;
import java.awt.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratePOJOActionController {
    @Inject
    EnvironmentDelegate environmentDelegate;
    @Inject
    MessageDelegate messageDelegate;
    @Inject
    GeneratorViewBinder generatorViewBinder;
    @Inject
    GenerationDelegate generationDelegate;

    @Inject
    public GeneratePOJOActionController() {
    }

    public void onActionHandled(AnActionEvent event) {
        try {
            proceed(event);
        } catch (RoboPluginException exception) {
            messageDelegate.onPluginExceptionHandled(exception);
        }
    }

    private void proceed(AnActionEvent event) throws RoboPluginException {
        final ProjectModel projectModel = environmentDelegate.obtainProjectModel(event);
        final DialogBuilder dialogBuilder = new DialogBuilder();
        final Window window = dialogBuilder.getWindow();

        generatorViewBinder.bindView(dialogBuilder, new GuiFormEventListener() {
            @Override
            public void onJsonDataObtained(GenerationModel generationModel) {
                window.dispose();
                generationDelegate.runGenerationTask(generationModel, projectModel);
            }
        });
    }
}
