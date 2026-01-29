plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(KotlinLibs.PATH)
}

dependencies {
    implementation("io.projectreactor.tools:blockhound:1.0.15.RELEASE")
}

tasks.bootJar { enabled = false }
tasks.jar { enabled = true }