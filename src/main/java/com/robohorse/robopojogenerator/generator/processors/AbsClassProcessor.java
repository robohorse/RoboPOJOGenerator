package com.robohorse.robopojogenerator.generator.processors;

import com.robohorse.robopojogenerator.generator.ClassItem;
import com.robohorse.robopojogenerator.generator.utils.ClassGenerateHelper;

import org.json.JSONObject;

import java.util.Set;

import javax.inject.Inject;

/**
 * This is the AbsClassProcessor class
 * Please put some info here.
 *
 * @author Wafer Li
 * @since 16/10/24 21:35
 */
public abstract class AbsClassProcessor {
    @Inject
    ClassGenerateHelper classGenerateHelper;

    public abstract void proceed(JSONObject jsonObject,
                                 String className,
                                 final Set<ClassItem> classItemSet);
}
