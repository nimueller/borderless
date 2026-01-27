pluginManagement {
    repositories {
        maven("https://maven.minecraftforge.net")
        maven("https://maven.parchmentmc.org")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.spongepowered.mixin") {
                useModule("org.spongepowered:mixingradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "BorderlessWindow"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("borderless-common")
include("borderless-forge")
include("borderless-neoforge")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
