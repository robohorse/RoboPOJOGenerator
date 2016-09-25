package com.robohorse.robopojogenerator.errors;

/**
 * Created by vadim on 25.09.16.
 */
public class FileWriteException extends RoboPluginException {
    public FileWriteException(String message) {
        super("File creation exception:", message);
    }
}
