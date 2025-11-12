rootProject.name = "message-server"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
include("jooq-config")

include("data")
include("data:data-post")

include("boot")
include("boot:boot-direct-message")
include("message-domain:domain-post")
include("message-application:application-post")
