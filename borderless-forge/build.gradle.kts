val forgeLoaderMinVersion: String by extra
val parchmentMappingsVersion: String by extra
val parchmentMinecraftVersion: String by extra
val minecraftVersion: String by extra
val forgeVersion: String by extra
val modId: String by extra

plugins {
    idea
    id("borderless.implementation")
    alias(libs.plugins.forgegradle)
    alias(libs.plugins.parchment)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

minecraft {
    mappings("parchment", "$parchmentMappingsVersion-$parchmentMinecraftVersion")
    reobf = false
    copyIdeResources = true

    runs {
        val client by creating {
            val borderless by mods.creating {
                property("forge.enabledGameTestNamespaces", modId)
            }
        }

        val server by creating {
            val borderless by mods.creating {
                property("forge.enabledGameTestNamespaces", modId)
                args("--nogui")
            }
        }

        configureEach {
            workingDirectory(project.file("run"))
            ideaModule = "${rootProject.name}.${project.name}.main"

            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
            property("forge.logging.console.level", "debug")
        }
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    filesMatching("META-INF/mods.toml") {
        expand(
            "loader_min_version" to forgeLoaderMinVersion,
            "version" to project.version,
            "modid" to modId,
        )
    }
}

tasks.jar {
    manifest {
        attributes(
            "Specification-Title" to project.name,
            "Specification-Vendor" to "nimueller",
            "Specification-Version" to project.version,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "nimueller",
        )
    }
}

sourceSets.forEach {
    val dir = layout.buildDirectory.dir("sourcesSets/${it.name}")
    it.output.setResourcesDir(dir.get())
    it.java.destinationDirectory.set(dir.get())
}
