package com.robohorse.robopojogenerator.services;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.FileWriteException;
import com.robohorse.robopojogenerator.generator.ClassItem;
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
    public FileWriterService() {
    }

    public void writeFile(ClassItem classItem, GenerationModel generationModel,
                          ProjectModel projectModel) throws RoboPluginException {
        final String path = projectModel.getDirectory().getVirtualFile().getPath();
        final String fileName = classItem.getClassName() + ".java";
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
                FileUtils.writeStringToFile(file, classItem.toString());
            }
        } catch (IOException e) {
            throw new FileWriteException(e.getMessage());
        }
    }
}
