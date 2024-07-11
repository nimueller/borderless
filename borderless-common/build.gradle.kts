plugins {
    id("borderless.common")
    application
}

application {
    mainClass = "de.nekeras.borderless.common.Main"
}

dependencies {
    compileOnly(libs.lombok)
    compileOnly(libs.slf4j)
    compileOnly(libs.glfw)
    compileOnly(libs.findbugs)

    annotationProcessor(libs.lombok)
}
