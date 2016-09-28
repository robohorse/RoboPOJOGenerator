package com.robohorse.robopojogenerator.utils;

import com.intellij.notification.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.robohorse.robopojogenerator.errors.RoboPluginException;
import groovy.lang.Singleton;

import javax.inject.Inject;

/**
 * Created by vadim on 24.09.16.
 */
@Singleton
public class MessageService {
    private static final String GROUP_DISPLAY_ID = "RoboPOJOGenerator";

    @Inject
    public MessageService() {
    }

    private static final NotificationGroup GROUP_DISPLAY_ID_INFO =
            new NotificationGroup(GROUP_DISPLAY_ID, NotificationDisplayType.BALLOON, true);

    public void onPluginExceptionHandled(RoboPluginException exception) {
        showMessage(exception.getMessage(), exception.getHeader());
    }

    private void showMessage(String message, String header) {
        Messages.showDialog(message, header, new String[]{"OK"}, -1, null);
    }

    public void logEventMessage(String title, String message) {
        Notifications.Bus.notify(new Notification(GROUP_DISPLAY_ID, title, message, NotificationType.INFORMATION));
    }

    public void showSuccessMessage() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Notification notification = GROUP_DISPLAY_ID_INFO
                        .createNotification("POJO generation: Success", NotificationType.INFORMATION);
                Project[] projects = ProjectManager.getInstance().getOpenProjects();
                Notifications.Bus.notify(notification, projects[0]);
            }
        });
    }
}
