plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.intellij)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.jetbrains.changelog) apply false
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(rootProject.libs.kotlin.stdlib)
        implementation(rootProject.libs.insert.koin)

        testImplementation(rootProject.libs.kotlin.test)
        testImplementation(rootProject.libs.io.mockk)
    }
}
