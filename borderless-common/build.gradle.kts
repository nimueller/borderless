plugins {
    id("borderless.common")
}

dependencies {
    compileOnly(libs.lombok)
    compileOnly(libs.slf4j)
    compileOnly(libs.glfw)
    compileOnly(libs.findbugs)

    annotationProcessor(libs.lombok)
}