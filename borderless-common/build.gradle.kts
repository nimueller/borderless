plugins {
    id("borderless.common")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    compileOnly("org.slf4j:log4j-over-slf4j:2.0.13")
    compileOnly("org.lwjgl:lwjgl-glfw:3.3.3")
    compileOnly("com.google.code.findbugs:annotations:3.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}