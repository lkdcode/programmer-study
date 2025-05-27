import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin(PluginVersion.KOTLIN_JVM.id) version PluginVersion.KOTLIN_JVM.version
    kotlin(PluginVersion.KOTLIN_SPRING.id) version PluginVersion.KOTLIN_SPRING.version
    kotlin(PluginVersion.KOTLIN_JPA.id) version PluginVersion.KOTLIN_JPA.version

    id(PluginVersion.SPRING_BOOT.id) version PluginVersion.SPRING_BOOT.version
    id(PluginVersion.SPRING_DEPENDENCY_MANAGEMENT.id) version PluginVersion.SPRING_DEPENDENCY_MANAGEMENT.version
}

group = Versioning.GROUP
version = Versioning.VALUE

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versioning.JAVA_VER)
    }
}

repositories {
    mavenCentral()
}

apply {
    from(SpringBootStaterLibs.PATH)
    from(SpringSecurityLibs.PATH)

    from(KotlinVersioning.PATH)
    from(DatabaseLibs.PATH)

    from(TestLibs.PATH)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = Versioning.JAVA_VER
}

tasks.withType<Test> {
    useJUnitPlatform()
}