plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)

    kotlin(Kotlin.WITH_JVM)
}

dependencies {
    implementation(project(":message-domain:domain-post"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-aop")
}