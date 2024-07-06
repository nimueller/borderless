pluginManagement {
    repositories {
        maven("https://maven.minecraftforge.net")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            plugin("forgegradle", "net.minecraftforge.gradle").version("6.0.+")
            plugin("neoforge", "net.neoforged.moddev").version("0.1.+")

            library("lombok", "org.projectlombok:lombok:1.18.+")
            library("glfw", "org.lwjgl:lwjgl-glfw:3.3.3")
            library("slf4j", "org.slf4j:log4j-over-slf4j:2.0.+")
            library("findbugs", "com.google.code.findbugs:annotations:3.0.0")
        }
    }
}

rootProject.name = "BorderlessWindow"
include("borderless-forge")
include("borderless-common")
include("borderless-neoforge")
