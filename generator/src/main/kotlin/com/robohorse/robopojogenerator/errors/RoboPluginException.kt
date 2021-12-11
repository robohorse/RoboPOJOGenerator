package com.robohorse.robopojogenerator.errors

open class RoboPluginException(
    val header: String,
    message: String?
) : Exception(message)

class FileWriteException(
    message: String?
) : RoboPluginException("File creation exception:", message)

class JSONStructureException :
    RoboPluginException("JSON exception:", "incorrect structure")

class PathException :
    RoboPluginException(
        "No directory was selected:",
        "You should choose directory for POJO files, before call RoboPOJOGenerator"
    )

class WrongClassNameException :
    RoboPluginException("Wrong class name:", "you should set root class name")
