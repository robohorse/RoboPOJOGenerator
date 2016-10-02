package com.robohorse.robopojogenerator.action;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;
import com.robohorse.robopojogenerator.generator.processors.ClassPostProcessor;
import com.robohorse.robopojogenerator.model.GenerationModel;
import com.robohorse.robopojogenerator.utils.FileWriter;
import com.robohorse.robopojogenerator.view.GeneratorViewBinder;
import com.robohorse.robopojogenerator.utils.MessageService;
import com.robohorse.robopojogenerator.utils.PathValidator;

import javax.inject.Inject;
import java.awt.*;
import java.util.Set;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionController {
    @Inject
    PathValidator pathValidator;
    @Inject
    MessageService messageService;
    @Inject
    GeneratorViewBinder generatorViewBinder;
    @Inject
    RoboPOJOGenerator roboPOJOGenerator;
    @Inject
    ClassPostProcessor classPostProcessor;
    @Inject
    FileWriter fileWriter;

    @Inject
    public ActionController() {
    }

    public void onActionHandled(AnActionEvent event) {
        try {
            proceed(event);
        } catch (RoboPluginException exception) {
            messageService.onPluginExceptionHandled(exception);
        }
    }

    private void proceed(AnActionEvent event) throws RoboPluginException {
        final Project project = event.getProject();
        final VirtualFile virtualFolder = event.getData(LangDataKeys.VIRTUAL_FILE);
        final String packageName = ProjectRootManager
                .getInstance(project)
                .getFileIndex()
                .getPackageNameByDirectory(virtualFolder);
        final PsiDirectory directory = pathValidator.checkPath(event);

        if (null != directory) {
            final DialogBuilder dialogBuilder = new DialogBuilder();
            final Window window = dialogBuilder.getWindow();

            generatorViewBinder.bindView(dialogBuilder, new GuiFormEventListener() {
                @Override
                public void onJsonDataObtained(GenerationModel generationModel) {
                    ProgressManager.getInstance().run(new Task.Backgroundable(project,
                            "RoboPOJO Generation", false) {

                        public void run(ProgressIndicator indicator) {
                            try {
                                generateFiles(generationModel, packageName, directory);
                            } catch (RoboPluginException e) {
                                messageService.onPluginExceptionHandled(e);
                            }
                            messageService.showSuccessMessage();
                            ProjectView.getInstance(project).refresh();
                            virtualFolder.refresh(true, true);
                            window.setVisible(false);
                        }
                    });
                }
            });
        }
    }

    private void generateFiles(GenerationModel model, String packageName, PsiDirectory directory)
            throws RoboPluginException {
        final Set<ClassItem> classItemSet = roboPOJOGenerator.generate(model);
        for (ClassItem classItem : classItemSet) {
            classItem.setPackagePath(packageName);
            classPostProcessor.proceed(classItem, model.getAnnotationItem());

            fileWriter.writeFile(directory, classItem, model.isRewriteClasses());
        }
    }
}
