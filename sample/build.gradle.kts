plugins {
    id("android-app-convention")
    id("kotlin-parcelize")
}

android {
    namespace = "net.humans.android.ui.wrappers.sample"
    viewBinding.enable = true
}

dependencies {
    implementation(libs.android.x.appCompat)
    implementation(libs.android.x.core)
    implementation(libs.android.x.activity.ktx)
    implementation(libs.android.x.lifecycle.viewmodel.ktx)

    implementation(projects.wrappers)
}
