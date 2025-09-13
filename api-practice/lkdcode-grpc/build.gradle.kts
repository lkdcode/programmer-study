plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.Alias.JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.Alias.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.Alias.SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.Alias.JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.Alias.LOMBOK) version Kotlin.KOTLIN_VERSION

    id(GRPCLibs.GOOGLE_PROTOBUF) version GRPCLibs.VERSION
}

group = Versioning.GROUP
version = Versioning.VERSION

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versioning.JAVA)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = SpringBoot.ID)
    apply(plugin = SpringBoot.DEPENDENCY_MANAGEMENT)
    apply(plugin = Kotlin.Id.JVM)
    apply(plugin = Kotlin.Id.SPRING)
    apply(plugin = GRPCLibs.GOOGLE_PROTOBUF)

    apply(from = rootProject.file(SpringBootLibs.PATH))
    apply(from = rootProject.file(KotlinLibs.PATH))
    apply(from = rootProject.file(GRPCLibs.PATH))
}

kapt {
    generateStubs = true
}