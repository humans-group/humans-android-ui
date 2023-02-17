plugins {
    id("android-library-convention")
    id("kotlin-parcelize")
    id("publish-library-convention")
}

android {
    namespace = "net.humans.android.ui.wrappers"
}

dependencies {
    implementation(libs.android.x.appCompat)
    implementation(libs.android.x.core)
}
