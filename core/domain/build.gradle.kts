plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.aurakai.auraframefx"
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

    // Enable Java 25 preview features
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    // Enable Java 25 preview features
    kotlinOptions {
        jvmTarget = "25"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xjvm-default=all",
            "-Xjdk-release=25"
        )
    }

    dependencies {
        // Pure business logic, no Android dependencies
        implementation(libs.kotlinx.coroutines.core)
        implementation(project(":core:common"))

        // Testing
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
    }

}
