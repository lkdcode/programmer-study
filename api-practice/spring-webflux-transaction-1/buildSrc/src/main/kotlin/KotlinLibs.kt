object KotlinLibs {
    const val PATH = "gradle/dep/kotlin-dep.gradle"

    const val JACKSON_MODULE_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val JSR305_STRICT_MODE = "-Xjsr305=strict"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2"
    const val COROUTINES_REACTOR = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1"

    private const val KOTEST_VERSION = "5.9.1"
    private const val KOTEST_SPRING_VERSION = "1.3.0"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:$KOTEST_VERSION"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:$KOTEST_VERSION"
    const val KOTEST_SPRING = "io.kotest.extensions:kotest-extensions-spring:$KOTEST_SPRING_VERSION"
}