plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "RoboPOJOGenerator"
include(":main")
include(":generator")
include(":core")
