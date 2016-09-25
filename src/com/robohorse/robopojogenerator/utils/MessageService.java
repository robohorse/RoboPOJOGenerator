package com.robohorse.robopojogenerator.utils;

import com.intellij.openapi.ui.Messages;
import com.robohorse.robopojogenerator.errors.RoboPluginException;

/**
 * Created by vadim on 24.09.16.
 */
public class MessageService {

    public void onPluginExceptionHandled(RoboPluginException exeption) {
        showMessage(exeption.getMessage(), exeption.getHeader());
    }

    private void showMessage(String message, String header) {
        Messages.showDialog(message, header, new String[]{"OK"}, -1, null);
    }
}
