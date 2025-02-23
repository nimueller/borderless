pluginManagement {
    repositories {
        maven("https://maven.minecraftforge.net")
        maven("https://maven.parchmentmc.org")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("forgegradle", "net.minecraftforge.gradle").version("[6.0.24,6.2)")
            plugin("neoforge", "net.neoforged.moddev").version("2.0.+")
            plugin("parchment", "org.parchmentmc.librarian.forgegradle").version("1.+")

            library("lombok", "org.projectlombok:lombok:1.18.+")
            library("glfw", "org.lwjgl:lwjgl-glfw:3.3.3")
            library("slf4j", "org.slf4j:log4j-over-slf4j:2.0.+")
            library("findbugs", "com.google.code.findbugs:annotations:3.0.0")
        }
    }
}

rootProject.name = "BorderlessWindow"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("borderless-common")
include("borderless-forge")
include("borderless-neoforge")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}
