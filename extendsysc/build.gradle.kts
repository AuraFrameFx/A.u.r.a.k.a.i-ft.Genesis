plugins {
    id("com.android.library")
    // Hilt and KSP are applied without `apply false` in the module
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp) // Correct position: Apply KSP before Hilt
    alias(libs.plugins.hilt)
}




android {
    namespace = "dev.aurakai.auraframefx.extendsysc"
    compileSdk = 36

    defaultConfig {
        // Enable multidex for the test APK and set a test runner
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Multidex support (needed for androidTest APK when method count > 64K)
}

tasks.register("moduleCStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE C - Ready (Java 25)") }
}
