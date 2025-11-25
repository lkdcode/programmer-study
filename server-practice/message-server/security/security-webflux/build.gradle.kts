plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.KAPT)
    kotlin(Kotlin.WITH_SPRING)
    kotlin(Kotlin.WITH_JPA)
    kotlin(Kotlin.WITH_LOMBOK)
}

apply {
    from(SpringSecurityLibs.PATH)
}