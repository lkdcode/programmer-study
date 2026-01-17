plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringframeworkLibs.PATH)
    from(KotlinLibs.PATH)
}

dependencies {
    implementation(project(":sb-domain"))
}