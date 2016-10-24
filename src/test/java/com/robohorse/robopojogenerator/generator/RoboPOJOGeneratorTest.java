package com.robohorse.robopojogenerator.generator;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.processors.ClassProcessor;
import com.robohorse.robopojogenerator.models.GenerationModel;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by vadim on 22.10.16.
 */
public class RoboPOJOGeneratorTest {
    @InjectMocks
    RoboPOJOGenerator roboPOJOGenerator;
    @Mock
    ClassProcessor classProcessor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generate() throws Exception {
        final GenerationModel generationModel = new GenerationModel
                .Builder()
                .setContent(new JSONObject().toString())
                .build();
        Set<ClassItem> classItemSet = roboPOJOGenerator.generate(generationModel);
        assertNotNull(classItemSet);
    }
}