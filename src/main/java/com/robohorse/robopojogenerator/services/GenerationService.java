package com.robohorse.robopojogenerator.services;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.ClassCreator;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Created by vadim on 02.10.16.
 */
public class GenerationService {
    @Inject
    ClassCreator classCreator;
    @Inject
    EnvironmentService environmentService;
    @Inject
    MessageService messageService;

    @Inject
    public GenerationService() {
    }

    public void runGenerationTask(final GenerationModel generationModel,
                                  final ProjectModel projectModel) {
        ProgressManager.getInstance().run(new Task.Backgroundable(projectModel.getProject(),
                "RoboPOJO Generation", false) {

            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    classCreator.generateFiles(generationModel, projectModel);
                    messageService.showSuccessMessage();

                } catch (RoboPluginException e) {
                    messageService.onPluginExceptionHandled(e);
                } finally {
                    indicator.stop();
                    environmentService.refreshProject(projectModel);
                }
            }
        });
    }
}
