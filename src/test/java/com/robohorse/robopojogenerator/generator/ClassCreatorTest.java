package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.common.ClassCreator;
import com.robohorse.robopojogenerator.models.ClassItemModel;
import com.robohorse.robopojogenerator.models.GenerationModel;
import com.robohorse.robopojogenerator.models.ProjectModel;
import com.robohorse.robopojogenerator.delegates.FileWriterDelegate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vadim on 22.10.16.
 */
public class ClassCreatorTest {
    @InjectMocks
    ClassCreator classCreator;
    @Mock
    RoboPOJOGenerator roboPOJOGenerator;
    @Mock
    FileWriterDelegate fileWriterDelegate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateFiles() throws Exception {
        final ClassItemModel classItemModel = new ClassItemModel("");
        final Set<ClassItemModel> classItemModelSet = new HashSet<ClassItemModel>();
        classItemModelSet.add(classItemModel);
        final GenerationModel generationModel = new GenerationModel
                .Builder()
                .build();
        final ProjectModel projectModel = new ProjectModel
                .Builder()
                .build();
        when(roboPOJOGenerator.generate(generationModel))
                .thenReturn(classItemModelSet);
        classCreator.generateFiles(generationModel, projectModel);
        verify(fileWriterDelegate)
                .writeFile(classItemModel, generationModel, projectModel);
    }
}