package com.robohorse.robopojogenerator.errors;

/**
 * Created by vadim on 25.09.16.
 */
public class WrongClassNameException extends RoboPluginException {
    public WrongClassNameException() {
        super("Wrong class name:", "you should set root class name");
    }
}
