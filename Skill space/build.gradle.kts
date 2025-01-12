import com.android.tools.r8.internal.id

// Top-level build.gradle file
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {

    }
}
