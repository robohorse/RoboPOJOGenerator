package com.robohorse.robopojogenerator.support;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by vadim on 24.09.16.
 */
public class ActionReceiver {
    private PathValidator pathValidator = new PathValidator();
    private GeneratorViewCreator viewCreator = new GeneratorViewCreator();
    private MessageService messageService = new MessageService();

    public void onActionHandled(AnActionEvent event){
        final String path = pathValidator.checkPath(event);
        if (null != path) {
            viewCreator.showView();

        } else {
            messageService.showPathErrorMessage();
        }
    }
}
