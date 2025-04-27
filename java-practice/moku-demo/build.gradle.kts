plugins {
    kotlin(PluginVersion.KOTLIN_JVM.id) version PluginVersion.KOTLIN_JVM.version
    kotlin(PluginVersion.KOTLIN_SPRING.id) version PluginVersion.KOTLIN_SPRING.version
    kotlin(PluginVersion.KOTLIN_JPA.id) version PluginVersion.KOTLIN_JPA.version

    id(PluginVersion.SPRING_BOOT.id) version PluginVersion.SPRING_BOOT.version
    id(PluginVersion.SPRING_DEPENDENCY_MANAGEMENT.id) version PluginVersion.SPRING_DEPENDENCY_MANAGEMENT.version
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(SpringBootStarter.WEB)
    implementation(SpringBootStarter.DATA_JPA)
    implementation(SpringBootStarter.VALIDATION)

    implementation(KotlinSet.JACKSON_MODULE_KOTLIN)
    implementation(KotlinSet.KOTLIN_REFLECT)

    implementation(DataSource.FLYWAY_CORE)
    implementation(DataSource.FLYWAY_MY_SQL)
    runtimeOnly(DataSource.MY_SQL_CONNECTOR)

    testImplementation(SpringBootStarter.TEST)
    testImplementation(TestSet.JUNIT5)
    testRuntimeOnly(TestSet.JUNIT_PLATFORM_LAUNCHER)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}