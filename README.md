# RoboPOJOGenerator

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RoboPOJOGenerator-green.svg?style=true)](https://android-arsenal.com/details/1/4429)
[![Build Status](https://travis-ci.org/robohorse/RoboPOJOGenerator.svg?branch=master)](https://travis-ci.org/robohorse/RoboPOJOGenerator)
[![Coverage Status](https://coveralls.io/repos/github/robohorse/RoboPOJOGenerator/badge.svg?branch=master)](https://coveralls.io/github/robohorse/RoboPOJOGenerator?branch=master)

Intellij Idea, Android Studio plugin for JSON to POJO conversion.

Generate Java and Kotlin POJO files from JSON: [GSON](https://github.com/google/gson), [AutoValue](https://github.com/google/auto/blob/master/value/userguide/index.md), [Logan Square](https://github.com/bluelinelabs/LoganSquare), [FastJSON](https://github.com/alibaba/fastjson), [Jackson](https://github.com/FasterXML/jackson), [Moshi](https://github.com/square/moshi), empty annotations template.
Supports: primitive types, multiple inner JSONArrays.

<p><img src="images/tutorial_v201.gif" width="100%" height="50%"></p>


# Download
get it and install from <a href="https://plugins.jetbrains.com/plugin/8634">plugin repository</a> or simply find it in "Preferences" -> "Plugins" -> "Browse Repositories" -> "RoboPOJOGenerator"

<p><img src="images/install_v201.png" width="100%" height="70%"></p>

# How to use

Select target package -> new -> Generate POJO from JSON

<p>
<img src="images/plugin_start_v201.png" height="300">
</p>

put JSON into window and select target POJO type

<p>
<img src="images/plugin_window_v201.png" height="300">
</p>

see log of changes

<p>
<img src="images/plugin_log_v201.png" height="200">
</p>

# People, who help
<ul>
<li>
<a href="https://github.com/wafer-li">wafer-li</a> - Kotlin support (release 1.7)
</li>
<li>
<a href="https://github.com/ccqy66">ccqy66</a> - toString support (release 1.8.1)
</li>
</ul>

# About
Copyright 2016 Vadim Shchenev, and licensed under the MIT license. No attribution is necessary but it's very much appreciated. Star this project if you like it.
