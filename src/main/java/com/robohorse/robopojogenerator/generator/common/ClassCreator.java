package com.robohorse.robopojogenerator.generator.common;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.generator.RoboPOJOGenerator;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import com.robohorse.robopojogenerator.delegates.FileWriterDelegate;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by vadim on 02.10.16.
 */
public class ClassCreator {
    @Inject
    RoboPOJOGenerator roboPOJOGenerator;
    @Inject
    FileWriterDelegate fileWriterDelegate;

    @Inject
    public ClassCreator() {
    }

    public void generateFiles(@NotNull GenerationModel generationModel,
                              @NotNull ProjectModel projectModel) throws RoboPluginException {
        final Set<ClassItemModel> classItemModelSet = roboPOJOGenerator.generate(generationModel);
        for (ClassItemModel classItemModel : classItemModelSet) {
            fileWriterDelegate.writeFile(classItemModel, generationModel, projectModel);
        }
    }
}
