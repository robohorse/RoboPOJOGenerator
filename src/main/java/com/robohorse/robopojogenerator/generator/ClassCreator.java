package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import com.robohorse.robopojogenerator.services.FileWriterService;
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
    FileWriterService fileWriterService;

    @Inject
    public ClassCreator() {
    }

    public void generateFiles(@NotNull GenerationModel generationModel,
                              @NotNull ProjectModel projectModel) throws RoboPluginException {
        final Set<ClassItem> classItemSet = roboPOJOGenerator.generate(generationModel);

        for (ClassItem classItem : classItemSet) {
            fileWriterService.writeFile(classItem, generationModel, projectModel);
        }
    }
}
