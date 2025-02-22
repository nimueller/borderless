plugins {
    java
    `java-library`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

val commonImplementation: Configuration by configurations.creating

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
            "Implementation-Vendor" to "nimueller",
        )
    }
}

tasks.processResources {
    val commonResources = project(":borderless-common").tasks.processResources

    from(commonResources) {
        include("**/*")
    }
}

tasks.shadowJar {
    configurations = listOf(commonImplementation)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
