buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(Flyway.JDBC_POSTGRESQL)
        classpath(Flyway.POSTGRESQL_PATH)
    }
}

plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    id(Flyway.ID) version Flyway.VERSION
    id(JOOQ.NS_STUDER_JOOQ) version JOOQ.VERSION

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(SpringSecurityLibs.PATH)
    from(DatabaseLibs.PATH)
    from(KotlinLibs.PATH)

    from(FlywayLibs.PATH)
    from(FlywayLibs.CONFIG_PATH)

    from(JooqLibs.PATH)
    from(JooqLibs.CONFIG_PATH)

    from(ApiDocsLibs.PATH)
    //    from(KafkaLibs.PATH)
}

dependencies {
    implementation(project(":sb-domain"))
    implementation(project(":sb-application"))
}

tasks.named("generateJooq") {
    dependsOn("flywayMigrate")
}

tasks.named("build") {
    dependsOn("generateJooq")
}