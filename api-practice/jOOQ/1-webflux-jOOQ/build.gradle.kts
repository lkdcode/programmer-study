buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(FlywayLibs.POSTGRESQL_PATH)
    }
}

plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_LOMBOK) version Kotlin.KOTLIN_VERSION

    id(JooqLibs.NS_STUDER_JOOQ) version JooqLibs.NS_STUDER_JOOQ_VERSION
    id(FlywayLibs.ID) version FlywayLibs.VERSION
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
    gradlePluginPortal()
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(DatabaseLibs.PATH)
    from(KotlinLibs.PATH)

    from(FlywayLibs.PATH)
    from(FlywayLibs.GRADLE_PATH)

    from(JooqLibs.PATH)
    from(JooqLibs.GRADLE_PATH)
}