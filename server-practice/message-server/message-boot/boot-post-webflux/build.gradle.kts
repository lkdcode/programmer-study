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
    from(SpringWebFluxLibs.PATH)
}

dependencies {
    implementation(project(":message-domain:domain-post"))
    implementation(project(":message-application:application-post"))
    implementation(project(":message-adapter:adapter-post-webflux"))
}