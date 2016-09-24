package com.robohorse.robopojogenerator.errors;

/**
 * Created by vadim on 24.09.16.
 */
public class PathException extends RoboPluginException {
    public PathException() {
        super("No directory was selected:", "You should choose directory for POJO files," +
                " before call RoboPOJOGenerator");
    }
}
