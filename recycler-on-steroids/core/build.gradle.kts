@file:Suppress("UnstableApiUsage")

plugins {
    id("android-library-convention")
    id("publish-library-convention")
    id("kotlin-parcelize")
}

group = "net.humans.android.ui.recycler"

android {
    namespace = "net.humans.android.ui.recycler.core"
    buildFeatures.viewBinding = true
}

mavenPublishing {
    coordinates(
        groupId = group.toString(),
        artifactId = project.name,
        version = project.version.toString()
    )
}

dependencies {

    implementation(projects.wrappers)

    with(libs.android) {
        implementation(x.core)
        implementation(x.recyclerView)
        implementation(x.viewBinding)
    }

    with(libs.mpp) {
        implementation(kotlinx.coroutines)
    }
}
