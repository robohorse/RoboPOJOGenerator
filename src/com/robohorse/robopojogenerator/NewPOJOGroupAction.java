package com.robohorse.robopojogenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.support.PathValidator;
import com.robohorse.robopojogenerator.support.GeneratorViewCreator;

/**
 * Created by vadim on 24.09.16.
 */
public class NewPOJOGroupAction extends AnAction {
    private PathValidator pathValidator = new PathValidator();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator();

    @Override
    public void actionPerformed(AnActionEvent event) {
        final String path = pathValidator.checkPath(event);
        if (null != path) {
            viewCreator.showView();
        }
    }
}
