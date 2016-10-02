package com.robohorse.robopojogenerator.services;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.custom.PathException;
import com.robohorse.robopojogenerator.errors.RoboPluginException;

import javax.inject.Inject;

/**
 * Created by vadim on 24.09.16.
 */
public class EnvironmentService {
    @Inject
    public EnvironmentService() {
    }

    public PsiDirectory checkPath(AnActionEvent event) throws RoboPluginException {
        Object pathItem = event.getData(CommonDataKeys.NAVIGATABLE);
        if (pathItem != null) {
            if (pathItem instanceof PsiDirectory) {
                return (PsiDirectory) pathItem;
            }
        }
        throw new PathException();
    }
}
