val modId: String by extra
val neoForgeVersion: String by extra
val neoForgeMappingsVersion: String by extra

plugins {
    id("borderless.common")
    id("borderless.implementation")
    alias(libs.plugins.neoforge)
}

neoForge {
    version = neoForgeVersion

    parchment {
        mappingsVersion = neoForgeMappingsVersion
        minecraftVersion = project.extra.get("minecraftParchmentVersion") as String
    }

    runs {
        val client by runs.creating {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        val server by runs.creating {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }

    mods {
        mods.create(modId) {
            sourceSet(sourceSets.main.get())
            dependency(project(":borderless-common"))
        }
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.processResources {
    doLast {
        val metaInfFolder = layout.buildDirectory.dir("resources/main/META-INF/").get()
        val oldFile = metaInfFolder.file("mods.toml")
        val newFile = metaInfFolder.file("neoforge.mods.toml")
        oldFile.asFile.renameTo(newFile.asFile)
    }
}