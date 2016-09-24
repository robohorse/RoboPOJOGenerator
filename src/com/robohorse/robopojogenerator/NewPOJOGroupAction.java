package com.robohorse.robopojogenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.action.ActionReceiver;

/**
 * Created by vadim on 24.09.16.
 */
public class NewPOJOGroupAction extends AnAction {
    private ActionReceiver actionReceiver = new ActionReceiver();

    @Override
    public void actionPerformed(AnActionEvent event) {
        actionReceiver.onActionHandled(event);
    }
}
