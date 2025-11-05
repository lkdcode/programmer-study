import dev.lkdcode.YmlService

plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_LOMBOK) version Kotlin.KOTLIN_VERSION

    id(JooqLibs.NS_STUDER_JOOQ) version JooqLibs.VERSION
}

group = Versioning.GROUP
version = Versioning.VERSION

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(Versioning.JAVA)
    }
}

repositories {
    mavenCentral()
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(DatabaseLibs.PATH)
    from(FlywayLibs.PATH)
    from(KotlinLibs.PATH)
    from(JooqLibs.PATH)
}

val databaseDriver: String = YmlService(project).flywayDriver
val databaseUrl: String = YmlService(project).flywayUrl
val databaseUser: String = YmlService(project).flywayUser
val databasePassword: String = YmlService(project).flywayPassword

jooq {
    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = databaseDriver
                    url = databaseUrl
                    user = databaseUser
                    password = databasePassword
                }
            }
        }
    }
}