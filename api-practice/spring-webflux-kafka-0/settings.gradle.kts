plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "spring-webflux-kafka-0"
include("kafka-common")
include("kafka-producer-0")
include("kafka-producer-1")
include("kafka-consumer-0")