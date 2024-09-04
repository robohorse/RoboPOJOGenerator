plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.jetbrains.intellij.module)
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
    implementation(rootProject.libs.kotlin.stdlib)
    implementation(rootProject.libs.insert.koin)

    testImplementation(rootProject.libs.kotlin.test)
    testImplementation(rootProject.libs.io.mockk)
}
