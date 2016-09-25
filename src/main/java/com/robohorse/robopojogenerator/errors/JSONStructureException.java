package com.robohorse.robopojogenerator.errors;

/**
 * Created by vadim on 24.09.16.
 */
public class JSONStructureException extends RoboPluginException {
    public JSONStructureException() {
        super("JSON exception:", "incorrect structure");
    }
}
