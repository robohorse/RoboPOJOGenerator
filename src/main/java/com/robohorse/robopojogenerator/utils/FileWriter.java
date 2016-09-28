package com.robohorse.robopojogenerator.utils;

import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.custom.FileWriteException;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
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
    public FileWriter() {
    }

    public void writeFile(PsiDirectory directory, ClassItem classItem, boolean rewrite) throws RoboPluginException {
        final String path = directory.getVirtualFile().getPath();
        final File file = new File(path + File.separator + classItem.getClassName() + ".java");
        try {
            if (file.exists() && rewrite) {
                file.delete();
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
