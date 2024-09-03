plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.kotlin.jvm) apply true
}

dependencies {
    implementation(project(":core", "default"))
    implementation(rootProject.libs.org.json)
    implementation(rootProject.libs.commons.io)
    implementation(rootProject.libs.google.guava)
}
