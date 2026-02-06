plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
    kotlin(Kotlin.WITH_JPA)
    kotlin(Kotlin.WITH_LOMBOK)

    id(JooqLibs.NS_STUDER_JOOQ)
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(DatabaseLibs.PATH)
    from(KotlinLibs.PATH)

    from(JooqLibs.PATH)
    from(JooqLibs.CONFIG_PATH)
}
