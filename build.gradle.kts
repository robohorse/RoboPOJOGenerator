plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.intellij.platform) apply true
    alias(libs.plugins.jetbrains.intellij.module) apply false
    alias(libs.plugins.ktlint)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        create("IU", rootProject.libs.versions.ide)
        pluginModule(implementation(project(":main")))
        bundledPlugin("com.intellij.java")

        pluginVerifier()
        zipSigner()
        instrumentationTools()
    }
}

tasks {
    buildSearchableOptions {
        enabled = false
    }
}
