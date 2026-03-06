group = "nl.knaw.huc.di.editem"
version = "1.0-SNAPSHOT"

val ktorVersion: String by project

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
//    maven {
//        url = uri("https://maven.huygens.knaw.nl/repository/")
//    }
}

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)

    // Apply the application plugin to add support for building a CLI application in Java.
    application
    kotlin("plugin.serialization") version "2.2.20"

    id("com.gradleup.shadow") version "9.2.2"
}

buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        gradlePluginPortal()
    }
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("io.ktor:ktor-client-cio:3.0.0")

    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.5.0")
    implementation("org.apache.logging.log4j:log4j-core:2.25.3")
    implementation("org.apache.logging.log4j:log4j:2.25.3")

    implementation("org.jetbrains.kotlinx:kotlinx-cli-jvm:0.3.6")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    implementation("org.redundent:kotlin-xml-builder:1.9.1")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")

    runtimeOnly("com.sun.xml.bind:jaxb-impl:3.0.1")

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(18)
}

application {
    // Define the main class for the application.
    mainClass = "nl.knaw.huc.di.editem.PageXML2TEIKt"
    applicationName = "pagexml2tei"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}
