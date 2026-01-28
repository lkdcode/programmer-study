plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringBootLibs.PATH)
    from(KotlinLibs.PATH)
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }