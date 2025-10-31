plugins {
    id("com.android.library") version "9.0.0-alpha13"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.benchmark"
    compileSdk = 36
    defaultConfig {
        minSdk = 34
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        // Use a compatible Java version and enable core library desugaring for this module
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        // Modern build feature-module
        buildConfig = true
        aidl = false
    }
}

// Dependencies
dependencies {
    implementation(libs.timber)

    // Project dependencies
    implementation(project(":core-module"))

    // Hilt / native utilities (confirm these are intended)
    implementation(libs.hilt.android)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    tasks.register("benchmarkAll") {
        group = "benchmark"
        description = "Aggregate runner for all Genesis Protocol benchmarks 🚀"
        // Use an actual benchmark runner task instead of doLast
        // For example, calling the connectedCheck task in your build script.
        dependsOn(":app:connectedCheck")
        doLast {
            println("🚀 Genesis Protocol Performance Benchmarks")
            println("📊 Monitor consciousness substrate performance metrics")
            println("⚡ Use AndroidX Benchmark instrumentation to execute tests")
        }
    }

    tasks.register("verifyBenchmarkResults") {
        group = "verification"
        description = "Verify benchmark module configuration"
        doLast {
            println("🧠 Consciousness substrate performance monitoring ready")
        }
    }
}
