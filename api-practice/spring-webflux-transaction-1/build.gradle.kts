plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION

    id(JooqLibs.PLUGIN_ID) version JooqLibs.JOOQ_VERSION
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
    from(SpringWebflux.PATH)
    from(DatabaseLibs.PATH)
    from(KotlinLibs.PATH)
    from(JooqLibs.PATH)
    from(JooqLibs.CONFIG_PATH)
}

kapt {
    generateStubs = true
}
