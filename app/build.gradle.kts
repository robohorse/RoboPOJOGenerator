fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("org.jetbrains.changelog") version "1.3.1"
}

dependencies {
    implementation(project(":generator", "default"))
    implementation(project(":core", "default"))

    implementation("com.fifesoft:rsyntaxtextarea:3.3.1")
}

tasks {

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))

        pluginDescription.set(
            rootDir.resolve("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { org.jetbrains.changelog.markdownToHTML(this) }
        )

        changeNotes.set(
            provider {
                changelog.run {
                    getOrNull(properties("pluginVersion")) ?: throw IllegalStateException("Please update changelog.")
                }.toHTML()
            }
        )
    }
}