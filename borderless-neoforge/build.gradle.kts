val modId: String by extra
val neoForgeVersion: String by extra
val neoForgeMappingsVersion: String by extra

plugins {
    id("borderless.common")
    alias(libs.plugins.neoforge)
}

neoForge {
    version = neoForgeVersion

    parchment {
        mappingsVersion = neoForgeMappingsVersion
        minecraftVersion = project.extra.get("minecraftVersion") as String
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
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4") { version { strictly("5.0.4") } }

    api(project(":borderless-common"))
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.processResources {
    doFirst {
        println("Trying to delete mods.toml")
        sourceSets.main.get().output.resourcesDir?.let {
            delete(it.resolve("META-INF/neoforge.mods.toml"))
            println("Deleted")
        }
    }

    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        include("META-INF/neoforge.mods.toml")
        expand("version" to project.version, "modid" to modId)
        println("Expand version to ${project.version}")
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
            "Implementation-Vendor" to "nimueller"
        )
    }
}

sourceSets.forEach {
    val dir = layout.buildDirectory.dir("sourcesSets/$it.name")
    it.output.setResourcesDir(dir)
    it.java.destinationDirectory = dir
}
