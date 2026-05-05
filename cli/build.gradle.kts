plugins {
    id("java")
    alias(libs.plugins.kotlin.jvm) apply true
    application
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.robohorse.robopojogenerator.cli.MainKt")
    applicationName = "robopojo"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":generator"))
    implementation(rootProject.libs.kotlin.stdlib)
    implementation(rootProject.libs.clikt)

    testImplementation(rootProject.libs.kotlin.test)
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.register<Jar>("fatJar") {
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.robohorse.robopojogenerator.cli.MainKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
