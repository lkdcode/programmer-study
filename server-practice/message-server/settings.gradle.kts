rootProject.name = "message-server"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
include("jooq-config")

include("data")
include("data:data-post")

include("boot")
include("boot:boot-direct-message")

include("message-domain:domain-post")
include("message-application:application-post")
include("message-adapter:adapter-post")
include("message-boot:boot-post")