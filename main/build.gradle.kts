plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.jetbrains.changelog) apply true
    alias(libs.plugins.jetbrains.intellij)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.1.2")
        bundledPlugin("com.intellij.java")

        pluginVerifier()
        zipSigner()
        instrumentationTools()
    }
    implementation(project(":generator", "default"))
    implementation(project(":core", "default"))

    implementation(rootProject.libs.fifesoft.rsyntaxtextarea)
}

intellijPlatform {
    pluginConfiguration {
        id = "com.robohorse.robopojogenerator"
        name = "RoboPOJOGenerator"
        version = "2.5.0"
        changeNotes = "Latest IDE support"
        ideaVersion {
            sinceBuild = "241"
            untilBuild = "242.*"
        }
    }
}
