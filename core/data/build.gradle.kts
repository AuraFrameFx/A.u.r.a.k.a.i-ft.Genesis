plugins {
    id("com.android.library")
    // Hilt and KSP are applied without `apply false` in the module
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp) // Correct position: Apply KSP before Hilt
    alias(libs.plugins.hilt)

}


android {
    namespace = "dev.aurakai.auraframefx.core.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    // Expose domain layer
    api(project(":core:domain"))

    // Data layer dependencies
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Common utilities
    implementation(project(":core:common"))

    // Testing
}
