plugins {
    java
    application
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

application {
    mainClass = "de.nekeras.borderless.common.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.lombok)
    compileOnly(libs.slf4j)
    compileOnly(libs.glfw)
    compileOnly(libs.findbugs)

    annotationProcessor(libs.lombok)
}
