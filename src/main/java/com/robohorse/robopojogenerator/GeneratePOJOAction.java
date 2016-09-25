package com.robohorse.robopojogenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.action.ActionController;

/**
 * Created by vadim on 26.09.16.
 */
public class GeneratePOJOAction extends AnAction {
    private ActionController actionController = new ActionController();

    @Override
    public void actionPerformed(AnActionEvent e) {
        actionController.onActionHandled(e);
    }
}
