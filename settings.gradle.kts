pluginManagement {
    repositories {
        maven("https://maven.minecraftforge.net")
        maven("https://maven.parchmentmc.org")
        gradlePluginPortal()
    }
}

rootProject.name = "BorderlessWindow"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("borderless-common")
include("borderless-forge")
include("borderless-neoforge")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}
