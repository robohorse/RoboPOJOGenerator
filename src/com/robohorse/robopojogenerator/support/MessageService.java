package com.robohorse.robopojogenerator.support;

import com.intellij.openapi.ui.Messages;

/**
 * Created by vadim on 24.09.16.
 */
public class MessageService {

    public void showPathErrorMessage() {
        showMessage("You should choose directory for POJO files, before call RoboPOJOGenerator",
                "No directory was selected:");
    }

    private void showMessage(String message, String header) {
        Messages.showDialog(message, header, new String[]{"OK"}, -1, null);
    }
}
