plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
    kotlin(Kotlin.WITH_JPA)
    kotlin(Kotlin.WITH_LOMBOK)

    id(FlywayLibs.ID)
    id(JooqLibs.NS_STUDER_JOOQ)
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(DatabaseLibs.PATH)
    from(KotlinLibs.PATH)

    from(FlywayLibs.PATH)
    from(FlywayLibs.CONFIG_PATH)

    from(JooqLibs.PATH)
    from(JooqLibs.CONFIG_PATH)
}

tasks.named("generateJooq") {
    dependsOn("flywayMigrate")
}

tasks.named("build") {
    dependsOn("generateJooq")
}