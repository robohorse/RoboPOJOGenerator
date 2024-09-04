plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.jetbrains.intellij.module) apply true
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity(rootProject.libs.versions.ide)
        instrumentationTools()
    }
    implementation(project(":generator", "default"))
    implementation(project(":core", "default"))

    implementation(rootProject.libs.fifesoft.rsyntaxtextarea)
    implementation(rootProject.libs.kotlin.stdlib)
    implementation(rootProject.libs.insert.koin)

    testImplementation(rootProject.libs.kotlin.test)
    testImplementation(rootProject.libs.io.mockk)
}
