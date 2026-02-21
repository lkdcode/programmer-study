object KotestLibs {
    const val PATH = "gradle/kotest-dep.gradle"

    private const val KOTEST_VERSION = "5.9.1"
    private const val MOCKK_VERSION = "1.13.12"
    private const val KOTEST_SPRING_VERSION = "1.3.0"

    const val RUNNER_JUNIT5 = "io.kotest:kotest-runner-junit5:$KOTEST_VERSION"
    const val ASSERTIONS_CORE = "io.kotest:kotest-assertions-core:$KOTEST_VERSION"
    const val MOCKK = "io.mockk:mockk:$MOCKK_VERSION"
    const val EXTENSIONS_SPRING = "io.kotest.extensions:kotest-extensions-spring:$KOTEST_SPRING_VERSION"
    const val TESTCONTAINERS = "org.testcontainers:testcontainers"
}