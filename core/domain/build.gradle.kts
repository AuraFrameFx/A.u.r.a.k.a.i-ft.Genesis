plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android) apply true
    id("com.google.devtools.ksp")
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
    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
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
