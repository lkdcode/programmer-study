buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    java

    id(SpringBoot.ID) version SpringBoot.VERSION apply false
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION apply false

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION apply false
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION apply false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versioning.JAVA)
    }
}

allprojects {
    group = Versioning.GROUP
    version = Versioning.VERSION

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}