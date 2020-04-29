package com.robohorse.robopojogenerator.models

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.robohorse.robopojogenerator.view.FrameworkVW

data class GenerationModel(
        val rewriteClasses: Boolean,
        val useKotlin: Boolean,
        val annotationEnum: FrameworkVW,
        val rootClassName: String,
        val content: String?,
        val useSetters: Boolean,
        val useGetters: Boolean,
        val useStrings: Boolean,
        val useKotlinSingleDataClass: Boolean,
        val useKotlinParcelable: Boolean,
        val kotlinNullableFields: Boolean
)

data class ProjectModel(
        val directory: PsiDirectory,
        val packageName: String?,
        val virtualFolder: VirtualFile,
        val project: Project
)

data class FieldModel(
        val classType: String? = null,
        val fieldName: String? = null,
        val fieldNameFormatted: String? = null,
        val annotation: String? = null
)
