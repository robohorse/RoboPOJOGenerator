package com.robohorse.robopojogenerator.services;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.FileWriteException;
import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.PostProcessorFactory;
import com.robohorse.robopojogenerator.generator.postprocessors.AbsPostProcessor;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by vadim on 25.09.16.
 */
public class FileWriterService {
    @Inject
    MessageService messageService;
    @Inject
    PostProcessorFactory factory;

    @Inject
    public FileWriterService() {
    }

    public void writeFile(ClassItem classItem, GenerationModel generationModel,
                          ProjectModel projectModel) throws RoboPluginException {
        final String path = projectModel.getDirectory().getVirtualFile().getPath();
        final String fileName = classItem.getClassName()
                + (generationModel.isUseKotlin() ? ".kt" : ".java");
        final File file = new File(path + File.separator + fileName);
        try {
            if (file.exists()) {
                if (generationModel.isRewriteClasses()) {
                    file.delete();
                    messageService.logEventMessage("updated " + fileName);
                } else {
                    messageService.logEventMessage("skipped " + fileName);
                }

            } else {
                messageService.logEventMessage("created " + fileName);
            }

            if (!file.exists()) {
                file.createNewFile();
                writeToFile(classItem, generationModel, projectModel, file);
            }
        } catch (IOException e) {
            throw new FileWriteException(e.getMessage());
        }
    }

    private void writeToFile(ClassItem classItem, GenerationModel generationModel,
                             ProjectModel projectModel, File file) throws IOException {
        final String content = prepareClass(classItem, generationModel, projectModel);
        FileUtils.writeStringToFile(file, content);
    }

    private String prepareClass(ClassItem classItem, GenerationModel generationModel,
                                ProjectModel projectModel) {
        classItem.setPackagePath(projectModel.getPackageName());
        final AbsPostProcessor postProcessor = factory.createPostProcessor(generationModel);
        return postProcessor.proceed(classItem, generationModel);
    }
}
