package com.robohorse.robopojogenerator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.robohorse.robopojogenerator.PluginApplication;
import org.jetbrains.annotations.NotNull;

public class GeneratePOJOAction extends AnAction {
    private final PluginApplication pluginApplication = new PluginApplication();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        pluginApplication.actionPerformed(e);
    }
}
