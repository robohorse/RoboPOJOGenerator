package com.robohorse.robopojogenerator.models

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory

data class GenerationModel(
    val rootClassName: String,
    val content: String,
    val rewriteClasses: Boolean = true,
    val useKotlin: Boolean = false,
    val annotationEnum: FrameworkVW = FrameworkVW.None(),
    val useSetters: Boolean = false,
    val useGetters: Boolean = false,
    val useStrings: Boolean = false,
    val useKotlinSingleDataClass: Boolean = false,
    val useKotlinParcelable: Boolean = false,
    val kotlinNullableFields: Boolean = true,
    val javaPrimitives: Boolean = false,
    val useTabsIndentation: Boolean = false,
    val useLombokValue: Boolean = false,
    val useMoshiAdapter: Boolean = false,
    val useKotlinDataClass: Boolean = true
) {
    init {
        require(rootClassName.isNotBlank()) { "rootClassName must not be blank" }
        require(content.isNotBlank()) { "content must not be blank" }
    }
}

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
