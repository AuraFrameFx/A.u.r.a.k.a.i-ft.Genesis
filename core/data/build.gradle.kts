plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.compose)

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
    implementation(libs.bundles.network)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Common utilities
    implementation(project(":core:common"))

    // Testing
    testImplementation(libs.bundles.testing.unit)
}
