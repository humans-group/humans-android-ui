@file:Suppress("UnstableApiUsage")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
include(":sample")
