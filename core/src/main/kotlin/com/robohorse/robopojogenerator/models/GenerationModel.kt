package com.robohorse.robopojogenerator.models

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

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
    val kotlinNullableFields: Boolean,
    val javaPrimitives: Boolean,
    val useTabsIndentation: Boolean,
    val useLombokValue: Boolean,
    val useMoshiAdapter: Boolean,
    val useKotlinDataClass: Boolean
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
    val annotation: String? = null,
    val visibility: Visibility = Visibility.NONE
)

enum class Visibility(val value: String) {
    NONE(""), PRIVATE("private")
}
