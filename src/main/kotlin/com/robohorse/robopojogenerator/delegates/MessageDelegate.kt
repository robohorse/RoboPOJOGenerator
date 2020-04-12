package com.robohorse.robopojogenerator.delegates

import com.intellij.notification.Notification
import com.intellij.notification.NotificationDisplayType.BALLOON
import com.intellij.notification.NotificationDisplayType.NONE
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.Messages
import com.robohorse.robopojogenerator.errors.RoboPluginException

class MessageDelegate {

    fun onPluginExceptionHandled(exception: RoboPluginException) {
        showMessage(exception.message, exception.header)
    }

    fun logEventMessage(message: String) {
        val notification = GROUP_DISPLAY_ID_LOG
                .createNotification(message, NotificationType.INFORMATION)
        sendNotification(notification)
    }

    fun showSuccessMessage() {
        val notification = GROUP_DISPLAY_ID_INFO
                .createNotification("POJO generation: Success", NotificationType.INFORMATION)
        sendNotification(notification)
    }

    private fun sendNotification(notification: Notification) {
        ApplicationManager.getApplication().invokeLater {
            val projects = ProjectManager.getInstance().openProjects
            Notifications.Bus.notify(notification, projects[0])
        }
    }

    private fun showMessage(message: String?, header: String) {
        Messages.showDialog(message, header, arrayOf("OK"), -1, null)
    }
}

private const val GROUP_DISPLAY = "RoboPOJOGenerator"
private const val GROUP_DISPLAY_LOG = "RoboPOJOGenerator LOG"
private val GROUP_DISPLAY_ID_INFO = NotificationGroup(GROUP_DISPLAY, BALLOON, true)
private val GROUP_DISPLAY_ID_LOG = NotificationGroup(GROUP_DISPLAY_LOG, NONE, true)
