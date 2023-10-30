@file:Suppress("MagicNumber")

import com.android.build.gradle.BaseExtension

configure<BaseExtension> {
    compileSdkVersion(34)

    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
