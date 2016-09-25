package com.robohorse.robopojogenerator.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;
import com.robohorse.robopojogenerator.support.GeneratorViewCreator;
import com.robohorse.robopojogenerator.support.MessageService;
import com.robohorse.robopojogenerator.support.PathValidator;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionController {
    private PathValidator pathValidator = new PathValidator();
    private MessageService messageService = new MessageService();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator();
    private RoboPOJOGenerator roboPOJOGenerator = new RoboPOJOGenerator();

    public void onActionHandled(AnActionEvent event) {
        try {
            proceed(event);
        } catch (RoboPluginException exception) {
            messageService.onPluginExceptionHandled(exception);
        }
    }

    private void proceed(AnActionEvent event) throws RoboPluginException {
        final String path = pathValidator.checkPath(event);
        if (null != path) {
            viewCreator.setGuiFormEventListener(new GuiFormEventListener() {
                @Override
                public void onJsonDataObtained(String content) {
                    generateFiles(content, path);
                }
            });
            viewCreator.showView();
        }
    }

    private void generateFiles(String content, String path) {
        Set<ClassItem> classItemSet = roboPOJOGenerator.generate(content);
        for (ClassItem classItem : classItemSet) {
            classItem.setPackagePath("com.robohorse.pojogenerator");
            classItem.setAnnotation("@JSON");
            //classPostProcessor.proceed(classItem);

            File file = new File(path + File.pathSeparator + classItem.getClassName() + ".java");
            try {
                file.createNewFile();
                FileUtils.writeStringToFile(file, classItem.toString());
            } catch (IOException e) {

            }
        }
    }

    private void resetProject() {
//        Project project = event.getData(CommonDataKeys.PROJECT);
//        if (project != null) {
//            ProjectView.getInstance(project).refresh();
//        }
    }
}
