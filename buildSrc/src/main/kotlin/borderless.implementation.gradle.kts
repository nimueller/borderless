plugins {
    java
    `java-library`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

val commonImplementation by configurations.creating

configurations.implementation {
    extendsFrom(commonImplementation)
}

dependencies {
    commonImplementation(project(":borderless-common"))
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

tasks.processResources {
    val commonResources = project(":borderless-common").tasks.processResources

    from(commonResources) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        exclude("logo.png")

        val loaderMinVersion = when (project.name) {
            "borderless-forge" -> project.extra.get("forgeLoaderMinVersion")
            "borderless-neoforge" -> project.extra.get("neoForgeLoaderMinVersion")
            else -> error("Unknown module")
        }

        expand(
            "loader_min_version" to loaderMinVersion,
            "version" to project.version,
            "modid" to project.extra.get("modId")
        )
    }

    from(commonResources) {
        include("logo.png")
    }
}

tasks.shadowJar {
    configurations = listOf(commonImplementation)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}