plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("se.solrike.sonarlint:sonarlint-gradle-plugin:2.0.0")
}
