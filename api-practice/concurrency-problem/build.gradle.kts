plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_LOMBOK) version Kotlin.KOTLIN_VERSION
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
    from(KotlinLibs.PATH)
    from(SpringBootLibs.PATH)
    from(DatabaseLibs.PATH)
    from(TestLibs.PATH)
}

kapt {
    generateStubs = true
}

tasks.test {
    useJUnitPlatform()
}