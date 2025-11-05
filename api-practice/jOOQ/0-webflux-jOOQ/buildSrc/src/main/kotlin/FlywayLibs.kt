object FlywayLibs {
    const val PATH = "gradle/dep/flyway-dep.gradle"
    const val GRADLE_PATH = "gradle/dep/flyway-config.gradle"

    const val ID = "org.flywaydb.flyway"

    const val VERSION = "11.15.0"
    const val POSTGRESQL_VERSION = "42.7.4"

    const val PLUGIN = "org.flywaydb.flyway:org.flywaydb.flyway.gradle.plugin:$VERSION"
    const val CORE = "org.flywaydb:flyway-core:$VERSION"
    const val POSTGRESQL = "org.flywaydb:flyway-database-postgresql:$VERSION"
    const val POSTGRESQL_PATH = "org.flywaydb:flyway-database-postgresql:$VERSION"

    const val POSTGRESQL_CONNECTOR = "org.postgresql:postgresql:$POSTGRESQL_VERSION"
    const val R2DBC_POSTGRESQL = "org.postgresql:r2dbc-postgresql"
}