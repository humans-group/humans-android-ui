import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath(libs.plugin.kotlin.gradle)
        classpath(libs.plugin.android.gradle)
        classpath(":build-logic")
    }
}

plugins {
    id("configuration-detekt-convention")
}

subprojects {
    apply(plugin = "configuration-ktlint-convention")

    group = "net.humans.android.ui"
    version = "2024.10.14"

    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    setupJavaTarget(this)
}

fun setupJavaTarget(project: Project) {
    project.tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
    project.tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }
}
