@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "humans-android-ui"

includeBuild("build-logic")

include(":wrappers")
include(":wrappers-compose-ktx")
include(":sample")
