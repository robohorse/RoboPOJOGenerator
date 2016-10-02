package com.robohorse.robopojogenerator.utils;

import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.FileWriteException;
import com.robohorse.robopojogenerator.generator.ClassItem;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by vadim on 25.09.16.
 */
public class FileWriter {
    @Inject
    MessageService messageService;

    @Inject
    public FileWriter() {
    }

    public void writeFile(PsiDirectory directory, ClassItem classItem, boolean rewrite)
            throws RoboPluginException {
        final String path = directory.getVirtualFile().getPath();
        final String fileName = classItem.getClassName() + ".java";
        final File file = new File(path + File.separator + fileName);
        try {
            if (file.exists()) {
                if (rewrite) {
                    file.delete();
                    messageService.logEventMessage("updated", fileName);
                } else {
                    messageService.logEventMessage("skipped", fileName);
                }

            } else {
                messageService.logEventMessage("created", fileName);
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
