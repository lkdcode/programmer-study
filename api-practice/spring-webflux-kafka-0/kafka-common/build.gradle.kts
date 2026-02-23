plugins {
    id(SpringBoot.ID)
    id(SpringBoot.DEPENDENCY_MANAGEMENT)
    kotlin(Kotlin.WITH_JVM)
    kotlin(Kotlin.WITH_SPRING)
}

apply {
    from(SpringWebFlux.PATH)
    from(KotlinLibs.PATH)
    from(KafkaLibs.PATH)
}

// 라이브러리 모듈 - 실행 가능 jar 불필요
tasks.bootJar { enabled = false }
tasks.jar { enabled = true }
