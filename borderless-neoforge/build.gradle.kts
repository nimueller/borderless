val neoForgeLoaderMinVersion: String by extra
val modId: String by extra
val neoForgeVersion: String by extra
val parchmentMinecraftVersion: String by extra
val parchmentMappingsVersion: String by extra

plugins {
    idea
    id("borderless.implementation")
    alias(libs.plugins.neoforge)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

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
            sourceSet(
                projects.borderlessCommon.dependencyProject.sourceSets.main
                    .get(),
            )
        }
    }
}

dependencies {
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(
            "loader_min_version" to neoForgeLoaderMinVersion,
            "version" to project.version,
            "modid" to modId,
        )
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
