import org.spongepowered.asm.gradle.plugins.struct.DynamicProperties

val forgeLoaderMinVersion: String by extra
val parchmentMappingsVersion: String by extra
val parchmentMinecraftVersion: String by extra
val minecraftVersion: String by extra
val forgeVersion: String by extra
val modId: String by extra

plugins {
    idea
    id("borderless.implementation")
    alias(libs.plugins.forge)
    alias(libs.plugins.parchment)
    alias(libs.plugins.mixin)
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

    embed(projects.borderlessCommon)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    annotationProcessor(variantOf(libs.mixin) { classifier("processor") })
}

mixin {
    add(sourceSets.main.get(), "mixins.$modId.refmap.json")
    config("mixins.$modId.json")

    val debug = debug as DynamicProperties
    debug.setProperty("verbose", true)
    debug.setProperty("export", true)
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
            "MixinConfigs" to "mixins.$modId.json",
        )
    }
}

sourceSets.forEach {
    val dir = layout.buildDirectory.dir("sourcesSets/${it.name}")
    it.output.setResourcesDir(dir.get())
    it.java.destinationDirectory.set(dir.get())
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
