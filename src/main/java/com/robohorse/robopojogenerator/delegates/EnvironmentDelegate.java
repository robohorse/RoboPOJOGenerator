package com.robohorse.robopojogenerator.delegates;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import com.robohorse.robopojogenerator.errors.custom.PathException;
import com.robohorse.robopojogenerator.models.ProjectModel;

import javax.inject.Inject;

/**
 * Created by vadim on 24.09.16.
 */
public class EnvironmentDelegate {
    @Inject
    public EnvironmentDelegate() {
    }

    public ProjectModel obtainProjectModel(AnActionEvent event) throws RoboPluginException {
        final PsiDirectory directory = checkPath(event);
        final Project project = event.getProject();
        final VirtualFile virtualFolder = event.getData(LangDataKeys.VIRTUAL_FILE);
        final String packageName = ProjectRootManager
                .getInstance(project)
                .getFileIndex()
                .getPackageNameByDirectory(virtualFolder);
        return new ProjectModel.Builder()
                .setDirectory(directory)
                .setPackageName(packageName)
                .setProject(project)
                .setVirtualFolder(virtualFolder)
                .build();
    }

    public void refreshProject(ProjectModel projectModel) {
        ProjectView.getInstance(projectModel.getProject()).refresh();
        projectModel.getVirtualFolder().refresh(false, true);
    }

    private PsiDirectory checkPath(AnActionEvent event) throws RoboPluginException {
        Object pathItem = event.getData(CommonDataKeys.NAVIGATABLE);
        if (pathItem != null) {
            if (pathItem instanceof PsiDirectory) {
                return (PsiDirectory) pathItem;
            }
        }
        throw new PathException();
    }
}
