plugins {
    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
}

group = Versioning.GROUP
version = Versioning.VERSION

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versioning.JAVA)
    }
}

repositories {
    mavenCentral()
}

apply {
    from(LogLibs.PATH)
}