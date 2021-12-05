package com.robohorse.robopojogenerator.errors

internal open class RoboPluginException(
    val header: String,
    message: String?
) : Exception(message)

internal class FileWriteException(
    message: String?
) : RoboPluginException("File creation exception:", message)

internal class JSONStructureException :
    RoboPluginException("JSON exception:", "incorrect structure")

internal class PathException :
    RoboPluginException(
        "No directory was selected:",
        "You should choose directory for POJO files, before call RoboPOJOGenerator"
    )

internal class WrongClassNameException :
    RoboPluginException("Wrong class name:", "you should set root class name")
