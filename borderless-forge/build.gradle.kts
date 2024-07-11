val minecraftVersion: String by extra
val forgeVersion: String by extra

plugins {
    id("borderless.common")
    id("borderless.implementation")
    alias(libs.plugins.forgegradle)
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

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

