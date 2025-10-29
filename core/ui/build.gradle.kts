plugins {
    id("com.android.library")
    // Hilt and KSP are applied without `apply false` in the module
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp) // Correct position: Apply KSP before Hilt
    alias(libs.plugins.hilt)

}

android {
    namespace = "dev.aurakai.auraframefx.core.ui"
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

    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
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
    // UI components
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt for ViewModels
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Common utilities
    implementation(project(":core:common"))

    // Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
}
