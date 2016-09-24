package com.robohorse.robopojogenerator.support;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;

/**
 * Created by vadim on 24.09.16.
 */
public class PathValidator {

    public String checkPath(AnActionEvent event) {
        Object pathItem = event.getData(CommonDataKeys.NAVIGATABLE);
        if (pathItem != null) {
            if (pathItem instanceof PsiDirectory) {
                PsiDirectory directory = (PsiDirectory) pathItem;
                return directory.getVirtualFile().getPath();

//                if (SettingsManager.isComposed(path)) {
//                    SettingsManager.removeComposedFolder(path);
//                } else {
//                    SettingsManager.addComposedFolder(path);
//                }
//
//                Project project = event.getData(CommonDataKeys.PROJECT);
//                if (project != null) {
//                    ProjectView.getInstance(project).refresh();
//                }
            }
        }
        return null;
    }
}
