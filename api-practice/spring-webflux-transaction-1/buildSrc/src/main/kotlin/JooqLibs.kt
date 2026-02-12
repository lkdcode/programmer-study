object JooqLibs {
    const val PATH = "gradle/dep/jooq-dep.gradle"
    const val CONFIG_PATH = "gradle/config/jooq-config.gradle"

    const val PLUGIN_ID = "org.jooq.jooq-codegen-gradle"

    const val TARGET_PACKAGE_NAME = "lkdcode.transaction.jooq"
    const val DEFAULT_DIRECTORY = "generated/jooq"

    const val JOOQ_VERSION = "3.20.8"

    const val JOOQ = "org.jooq:jooq:$JOOQ_VERSION"
    const val JOOQ_KOTLIN = "org.jooq:jooq-kotlin:$JOOQ_VERSION"
    const val JOOQ_KOTLIN_COROUTINES = "org.jooq:jooq-kotlin-coroutines:$JOOQ_VERSION"

    const val JOOQ_META_EXTENSIONS = "org.jooq:jooq-meta-extensions:$JOOQ_VERSION"
}
