@file:Suppress("UnstableApiUsage")

plugins {
    id("android-library-convention")
    id("publish-library-convention")
}

group = "net.humans.android.ui.recycler.di"

android {
    namespace = "net.humans.android.ui.recycler.di"
}

mavenPublishing {
    coordinates(
        groupId = group.toString(),
        artifactId = project.name,
        version = project.version.toString()
    )
}

dependencies {
    implementation(projects.recyclerOnSteroids.core)

    implementation(libs.mpp.koin)
}
