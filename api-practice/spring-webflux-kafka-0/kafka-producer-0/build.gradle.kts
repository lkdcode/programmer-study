plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringWebFlux.PATH)
    from(KotlinLibs.PATH)
    from(KafkaLibs.PATH)
}