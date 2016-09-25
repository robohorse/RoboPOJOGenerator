package com.robohorse.robopojogenerator.action;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;
import com.robohorse.robopojogenerator.generator.processors.ClassPostProcessor;
import com.robohorse.robopojogenerator.utils.FileWriter;
import com.robohorse.robopojogenerator.utils.GeneratorViewCreator;
import com.robohorse.robopojogenerator.utils.MessageService;
import com.robohorse.robopojogenerator.utils.PathValidator;

import javax.swing.*;
import java.util.Set;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionController {
    private PathValidator pathValidator = new PathValidator();
    private MessageService messageService = new MessageService();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator();
    private RoboPOJOGenerator roboPOJOGenerator = new RoboPOJOGenerator();
    private ClassPostProcessor classPostProcessor = new ClassPostProcessor();
    private FileWriter fileWriter = new FileWriter();

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
        final String packageName = ProjectRootManager.getInstance(project).getFileIndex().getPackageNameByDirectory(virtualFolder);
        final PsiDirectory directory = pathValidator.checkPath(event);

        if (null != directory) {
            viewCreator.setGuiFormEventListener(new GuiFormEventListener() {
                @Override
                public void onJsonDataObtained(String content, String rootClassName,
                                               AnnotationItem annotationItem, JFrame jFrame) {

                    try {
                        generateFiles(content, rootClassName, packageName, directory, annotationItem);
                        resetProject(project);
                    } catch (RoboPluginException e) {
                        messageService.onPluginExceptionHandled(e);
                    }
                    jFrame.setVisible(false);
                    virtualFolder.refresh(false, true);
                    messageService.showSuccessMessage();
                }
            });
            viewCreator.showView();
        }
    }

    private void generateFiles(String content, String rootClassName, String packageName,
                               PsiDirectory directory, AnnotationItem annotationItem)
            throws RoboPluginException {

        Set<ClassItem> classItemSet = roboPOJOGenerator.generate(content, rootClassName);

        for (ClassItem classItem : classItemSet) {
            classItem.setPackagePath(packageName);

            classPostProcessor.proceed(classItem, annotationItem);
            fileWriter.writeFile(directory, classItem);
        }
    }

    private void resetProject(Project project) {
        if (project != null) {
            ProjectView.getInstance(project).refresh();
        }
    }
}
