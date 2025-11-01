import org.jooq.meta.kotlin.strategy
import org.yaml.snakeyaml.Yaml


plugins {
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_LOMBOK) version Kotlin.KOTLIN_VERSION

    id(JOOQ.NS_STUDER_JOOQ) version JOOQ.VERSION
}

buildscript {
    dependencies {
        classpath("org.yaml:snakeyaml:2.2")
    }
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
    from(SpringBootReactive.PATH)
    from(DatabaseLibs.PATH)
    from(FlywayLibs.PATH)
    from(KotlinLibs.PATH)
    from(JooqLibs.PATH)
}

ext["jooq.version"] = JooqLibs.JOOQ_VERSION

tasks.withType<Test> {
    useJUnitPlatform()
}

val profile = (findProperty("spring.profiles.active") as String?) ?: "local"
val ymlFile = file("src/main/resources/application-$profile.yml")

val yml: Map<String, Any?> =
    Yaml().load<Map<String, Any?>>(ymlFile.readText()) ?: emptyMap()

fun Map<String, Any?>.nav(vararg keys: String): Any? {
    var cur: Any? = this
    for (k in keys) cur = (cur as? Map<*, *>)?.get(k) ?: return null
    return cur
}

val jdbcUrl = (yml.nav("spring", "flyway", "url")).toString()
val jdbcUser = (yml.nav("spring", "r2dbc", "username")).toString()
val jdbcPass = (yml.nav("spring", "r2dbc", "password")).toString()

jooq {
    version.set(JooqLibs.JOOQ_VERSION)
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)

            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = jdbcUrl
                    user = jdbcUser
                    password = jdbcPass
                }

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"

//                    strategy {
//                        name = JooqLibs.STRATEGY_NAME
//                    }

                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = "flyway_schema_history"
                    }

                    generate.apply {
                        isJavaTimeTypes = true
                        isDeprecated = false
                        isFluentSetters = true
                        isRecords = true
                        isDaos = false

                        isKotlinNotNullPojoAttributes = true
                        isKotlinNotNullRecordAttributes = true
                        isKotlinNotNullInterfaceAttributes = true
                    }

                    target.apply {
                        packageName = JooqLibs.TARGET_PACKAGE_NAME
                        directory = layout.buildDirectory.dir(JooqLibs.DEFAULT_DIRECTORY).get().asFile.path
                    }
                }
            }
        }
    }
}

sourceSets["main"].java {
    srcDir(layout.buildDirectory.dir(JooqLibs.DEFAULT_DIRECTORY).get().asFile.path)
}