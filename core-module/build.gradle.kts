plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.aurakai.auraframefx.core.module"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
        aidl = true
        shaders = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Java compatibility / desugaring
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
        isCoreLibraryDesugaringEnabled = true
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }

        dependencies {

            // Module dependency
            api(project(":list"))

            // Concurrency and serialization
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.coroutines)
            implementation(libs.kotlinx.serialization.json)
            coreLibraryDesugaring(libs.desugar.jdk.libs)
            // File operations and compression
            implementation(libs.commons.io)
            implementation(libs.commons.compress)
            implementation(libs.xz)

            // Logging API only (do not bind implementation at runtime for libraries)
            implementation(libs.slf4j.api)

            // Testing (JUnit 5)
            testImplementation(libs.junit.jupiter.api)
            testRuntimeOnly(libs.junit.jupiter.engine)
            testImplementation(libs.mockk)
        }
    }
}
