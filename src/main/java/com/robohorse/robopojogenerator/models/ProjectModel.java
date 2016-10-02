package com.robohorse.robopojogenerator.models;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;

/**
 * Created by vadim on 02.10.16.
 */
public class ProjectModel {
    private PsiDirectory directory;
    private String packageName;
    private VirtualFile virtualFolder;
    private Project project;

    public PsiDirectory getDirectory() {
        return directory;
    }

    public String getPackageName() {
        return packageName;
    }

    public VirtualFile getVirtualFolder() {
        return virtualFolder;
    }

    public Project getProject() {
        return project;
    }

    public static class Builder {
        private ProjectModel instance;

        public Builder() {
            instance = new ProjectModel();
        }

        public Builder setDirectory(PsiDirectory directory) {
            instance.directory = directory;
            return this;
        }

        public Builder setPackageName(String packageName) {
            instance.packageName = packageName;
            return this;
        }

        public Builder setVirtualFolder(VirtualFile virtualFolder) {
            instance.virtualFolder = virtualFolder;
            return this;
        }

        public Builder setProject(Project project) {
            instance.project = project;
            return this;
        }

        public ProjectModel build() {
            return instance;
        }
    }
}
