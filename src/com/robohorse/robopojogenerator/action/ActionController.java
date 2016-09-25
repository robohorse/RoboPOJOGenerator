package com.robohorse.robopojogenerator.action;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.AnnotationItem;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;
import com.robohorse.robopojogenerator.generator.processors.ClassPostProcessor;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;
import com.robohorse.robopojogenerator.utils.FileWriter;
import com.robohorse.robopojogenerator.utils.GeneratorViewCreator;
import com.robohorse.robopojogenerator.utils.MessageService;
import com.robohorse.robopojogenerator.utils.PathValidator;

import java.util.Set;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionController {
    private PathValidator pathValidator = new PathValidator();
    private MessageService messageService = new MessageService();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator();
    private RoboPOJOGenerator roboPOJOGenerator = new RoboPOJOGenerator();
    private ClassGenerateHelper classGenerateHelper = new ClassGenerateHelper();
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
        final PsiDirectory directory = pathValidator.checkPath(event);
        final Project project = event.getData(CommonDataKeys.PROJECT);
        if (null != directory) {
            viewCreator.setGuiFormEventListener(new GuiFormEventListener() {
                @Override
                public void onJsonDataObtained(String content) {
                    try {
                        generateFiles(content, directory, AnnotationItem.GSON);
                        resetProject(project);
                    } catch (RoboPluginException e) {
                        messageService.onPluginExceptionHandled(e);
                    }
                }
            });
            viewCreator.showView();
        }
    }

    private void generateFiles(String content, PsiDirectory directory, AnnotationItem annotationItem)
            throws RoboPluginException {

        Set<ClassItem> classItemSet = roboPOJOGenerator.generate(content);
        //TODO
        //JavaDirectoryService.getInstance().getPackage(directory).getQualifiedName();
        final String packageName = classGenerateHelper.resolvePackage(directory
                .getVirtualFile().getPath());

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
