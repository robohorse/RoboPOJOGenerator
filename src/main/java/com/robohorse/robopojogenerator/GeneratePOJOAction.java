package com.robohorse.robopojogenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.action.ActionController;
import com.robohorse.robopojogenerator.injections.Injector;

import javax.inject.Inject;

/**
 * Created by vadim on 26.09.16.
 */
public class GeneratePOJOAction extends AnAction {
    @Inject
    ActionController actionController;

    public GeneratePOJOAction() {
        Injector.initModules();
        Injector.getAppComponent().inject(this);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        actionController.onActionHandled(e);
    }
}
