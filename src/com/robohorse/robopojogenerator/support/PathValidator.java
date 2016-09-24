package com.robohorse.robopojogenerator.support;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
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
                String path = directory.getVirtualFile().getPath();

//                if (SettingsManager.isComposed(path)) {
//                    SettingsManager.removeComposedFolder(path);
//                } else {
//                    SettingsManager.addComposedFolder(path);
//                }

                Project project = event.getData(CommonDataKeys.PROJECT);
                if (project != null) {
                    ProjectView.getInstance(project).refresh();
                }

                return path;
            }
        }
        showMessage("You should choose directory for POJO files, before call RoboPOJOGenerator",
                "No directory was selected:");

        return null;
    }

    private void showMessage(String message, String header) {
        Messages.showDialog(message, header, new String[]{"OK"}, -1, null);
    }
}
