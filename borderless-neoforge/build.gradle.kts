val modId: String by extra
val neoForgeVersion: String by extra
val parchmentMinecraftVersion: String by extra
val parchmentMappingsVersion: String by extra

plugins {
    id("borderless.common")
    id("borderless.implementation")
    alias(libs.plugins.neoforge)
}

neoForge {
    version = neoForgeVersion

    parchment {
        minecraftVersion = parchmentMinecraftVersion
        mappingsVersion = parchmentMappingsVersion
    }

    runs {
        val client by creating {
            client()
        }

        val server by creating {
            server()
            programArgument("--nogui")
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
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