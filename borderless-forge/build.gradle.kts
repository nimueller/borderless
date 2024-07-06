val minecraftVersion: String by extra
val forgeVersion: String by extra

plugins {
    id("borderless.common")
    id("net.minecraftforge.gradle")
}

minecraft {
    mappings("official", minecraftVersion)
    reobf = false
    copyIdeResources = true

    val client by runs.creating {
        workingDirectory(project.file("run"))

        property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
        property("forge.logging.console.level", "debug")

        val borderless by mods.creating {
            source(sourceSets.main.get())
        }
    }

    val server by runs.creating {
        workingDirectory(project.file("run"))

        property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
        property("forge.logging.console.level", "debug")

        val borderless by mods.creating {
            source(sourceSets.main.get())
        }
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
    implementation("net.sf.jopt-simple:jopt-simple:5.0.4") { version { strictly("5.0.4") } }
}


tasks.processResources {
    doFirst {
        println("Trying to delete mods.toml")
        sourceSets.main.get().output.resourcesDir?.let {
            delete(it.resolve("META-INF/mods.toml"))
            println("Deleted")
        }
    }

    from(sourceSets.main.get().resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        include("META-INF/mods.toml")
        expand("version" to project.version)
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
