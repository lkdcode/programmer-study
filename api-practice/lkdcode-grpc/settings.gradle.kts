plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "lkdcode-grpc"
include("fruit-contracts")
include("tomato-grpc")
include("kiwi-grpc")

include(":fruit-contracts", ":kiwi-grpc")