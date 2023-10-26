plugins {
    id("android-library-convention")
    id("kotlin-parcelize")
    id("publish-library-convention")
}

android {
    namespace = "net.humans.android.ui.wrappers.compose"
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.5.1"
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    implementation(libs.android.x.appCompat)
    implementation(libs.android.x.core)
    implementation(projects.wrappers)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
}
