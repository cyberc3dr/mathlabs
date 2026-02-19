plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
}

group = "ru.cyberc3dr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

// Необходимые вещи, которые я оставляю во всех проектах,
// чтобы не настраивать их каждый раз

kotlin.jvmToolchain(21)

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 21
}

tasks.withType<Jar> {
    destinationDirectory = file("$rootDir/build")
    archiveVersion = ""
}

sourceSets.main {
    kotlin.srcDir("src")
    resources.srcDir("resources")
}
