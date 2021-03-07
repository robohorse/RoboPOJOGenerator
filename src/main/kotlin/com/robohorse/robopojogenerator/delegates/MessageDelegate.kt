package com.robohorse.robopojogenerator.delegates

import com.intellij.notification.*
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.Messages
import com.robohorse.robopojogenerator.errors.RoboPluginException


class MessageDelegate {

    fun onPluginExceptionHandled(exception: RoboPluginException) {
        showMessage(exception.message, exception.header)
    }

    fun logEventMessage(message: String) {
        sendNotification(message, NotificationType.WARNING)
    }

    fun showSuccessMessage() {
        sendNotification(TITLE_SUCCESS, NotificationType.INFORMATION)
    }

    private fun sendNotification(message: String, type: NotificationType) {
        ApplicationManager.getApplication().invokeLater {
            val projects = ProjectManager.getInstance().openProjects
            if (ApplicationInfo.getInstance().build.baselineVersion >= NEW_API_VERSION) {
                showNewApiMessage(message, type, projects[0])
            } else {
                showDeprecatedApiMessage(message, type, projects[0])
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun showDeprecatedApiMessage(message: String, type: NotificationType, project: Project) {
        NotificationGroup(
            GROUP_DISPLAY, NotificationDisplayType.BALLOON, true
        ).createNotification(message, type).apply {
            Notifications.Bus.notify(this, project)
        }
    }

    private fun showNewApiMessage(message: String, type: NotificationType, project: Project) {
        NotificationGroupManager.getInstance().getNotificationGroup(GROUP_ID)
            .createNotification(message, type)
            .notify(project)
    }

    private fun showMessage(message: String?, header: String) {
        Messages.showDialog(message, header, arrayOf(TITLE_OK), -1, null)
    }
}

private const val TITLE_OK = "OK"
private const val TITLE_SUCCESS = "POJO generation: Success"
private const val GROUP_ID = "RoboPOJO Generator"
private const val NEW_API_VERSION = 203
private const val GROUP_DISPLAY = "RoboPOJOGenerator"
