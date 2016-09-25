package com.robohorse.robopojogenerator.action;

import com.robohorse.robopojogenerator.generator.AnnotationItem;

import javax.swing.*;

/**
 * Created by vadim on 24.09.16.
 */
public interface GuiFormEventListener {

    void onJsonDataObtained(String content, String rootClassName, AnnotationItem annotationItem, JFrame jFrame);
}
