import dev.lkdcode.YmlParser


buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(FlywayLibs.DB_POSTGRESQL)
        classpath(FlywayLibs.POSTGRESQL_CONNECTOR)
    }
}

plugins {
    java
    id(SpringBoot.ID) version SpringBoot.VERSION
    id(SpringBoot.DEPENDENCY_MANAGEMENT) version SpringBoot.DEPENDENCY_MANAGEMENT_VERSION

    kotlin(Kotlin.WITH_JVM) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.KAPT) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_SPRING) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_JPA) version Kotlin.KOTLIN_VERSION
    kotlin(Kotlin.WITH_LOMBOK) version Kotlin.KOTLIN_VERSION

    id(FlywayLibs.ID) version FlywayLibs.VERSION
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
    gradlePluginPortal()
}

apply {
    from(SpringWebFluxLibs.PATH)
    from(DatabaseLibs.PATH)
    from(FlywayLibs.PATH)
    from(KotlinLibs.PATH)
    from(JooqLibs.PATH)
}

val databaseDriver: String = YmlParser(project).flywayDriver
val databaseUrl: String = YmlParser(project).flywayUrl
val databaseUser: String = YmlParser(project).flywayUser
val databasePassword: String = YmlParser(project).flywayPassword


val buildSrcClasspath = files(
    rootProject.file("buildSrc/build/classes/kotlin/main"),
    rootProject.file("buildSrc/build/classes/java/main")
)

dependencies {
    jooqGenerator(buildSrcClasspath)
    jooqGenerator("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
}

jooq {
    version = JooqLibs.JOOQ_VERSION

    configurations {
        create("main") {
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = databaseDriver
                    url = databaseUrl
                    user = databaseUser
                    password = databasePassword
                }

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"

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

                    strategy.apply {
                        name = JooqLibs.STRATEGY_NAME
                    }

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

val flywaySchema: String = YmlParser(project).flywaySchema
val flywayLocations: List<String> = YmlParser(project).flywayLocations


flyway {
    driver = databaseDriver
    url = databaseUrl
    user = databaseUser
    password = databasePassword
    defaultSchema = flywaySchema
    locations = flywayLocations.toTypedArray()
    cleanDisabled = false
}

tasks.named("flywayMigrate") {
    dependsOn("processResources")
}

tasks.named("flywayClean") {
    dependsOn("processResources")
}

tasks.named("flywayInfo") {
    dependsOn("processResources")
}

tasks.named("generateJooq") {
    dependsOn("flywayMigrate")
}

tasks.register("printGradleClassloaders") {
    doLast {
        val cl = this::class.java.classLoader
        fun dump(name: String, loader: ClassLoader?) {
            println("[$name]")
            var cur = loader
            var depth = 0
            while (cur != null) {
                val indent = "  ".repeat(depth)
                println("${indent}- ${cur::class.java.name}")
                cur = cur.parent
                depth++
            }
            println("${"  ".repeat(depth)}- <BootstrapClassLoader (null)>")
            println()
        }
        dump("Gradle Script CL", cl)
        dump("System(Application) CL", ClassLoader.getSystemClassLoader())
        dump("Platform CL", ClassLoader.getPlatformClassLoader())
    }
}