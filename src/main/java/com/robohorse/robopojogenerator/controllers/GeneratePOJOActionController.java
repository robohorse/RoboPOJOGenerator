package com.robohorse.robopojogenerator.controllers;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogBuilder;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.listeners.GuiFormEventListener;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import com.robohorse.robopojogenerator.services.EnvironmentService;
import com.robohorse.robopojogenerator.services.GenerationService;
import com.robohorse.robopojogenerator.services.MessageService;
import com.robohorse.robopojogenerator.view.GeneratorViewBinder;

import javax.inject.Inject;
import java.awt.*;

/**
 * Created by vadim on 24.09.16.
 */
public class GeneratePOJOActionController {
    @Inject
    EnvironmentService environmentService;
    @Inject
    MessageService messageService;
    @Inject
    GeneratorViewBinder generatorViewBinder;
    @Inject
    GenerationService generationService;

    @Inject
    public GeneratePOJOActionController() {
    }

    public void onActionHandled(AnActionEvent event) {
        try {
            proceed(event);
        } catch (RoboPluginException exception) {
            messageService.onPluginExceptionHandled(exception);
        }
    }

    private void proceed(AnActionEvent event) throws RoboPluginException {
        final ProjectModel projectModel = environmentService.obtainProjectModel(event);
        final DialogBuilder dialogBuilder = new DialogBuilder();
        final Window window = dialogBuilder.getWindow();

        generatorViewBinder.bindView(dialogBuilder, new GuiFormEventListener() {
            @Override
            public void onJsonDataObtained(GenerationModel generationModel) {
                window.dispose();
                generationService.runGenerationTask(generationModel, projectModel);
            }
        });
    }
}
