package com.robohorse.robopojogenerator.errors;

/**
 * Created by vadim on 24.09.16.
 */
public class RoboPluginException extends Exception {
    private String header;

    public RoboPluginException(String header, String message) {
        super(message);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
