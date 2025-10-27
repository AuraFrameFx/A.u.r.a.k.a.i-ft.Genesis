plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.collab.canvas"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
    }


    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }


    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
    dependencies {
        // Module dependencies - depend on core modules only
        implementation(project(":core:domain"))
        implementation(project(":core:data"))
        implementation(project(":core:ui"))
        implementation(project(":core:common"))
        implementation(libs.libsu.io)
        // AndroidX & Jetpack
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.material)
        implementation(libs.bundles.lifecycle)
        implementation(libs.bundles.room)
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)

        // DI
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)

        // Desugaring
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        // Kotlin
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.bundles.coroutines)

        // Networking
        implementation(libs.bundles.network)

        // Firebase
        implementation(platform(libs.firebase.bom))
        implementation(libs.bundles.firebase)
        implementation(libs.firebase.auth.ktx)

        // 3rd Party UI

        // Local Libs - Removed Xposed API as not needed for collab-canvas
        // compileOnly(files("libs/api-82.jar"))
        // compileOnly(files("libs/api-82-sources.jar"))

        // Testing
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing)
        androidTestImplementation(libs.androidx.benchmark.junit4)
        androidTestImplementation(libs.androidx.test.uiautomator)
        debugImplementation(libs.leakcanary.android)
    }

    tasks.register("collabStatus") {
        group = "aegenesis"
        doLast {
            println("COLLAB CANVAS - Ready (Java 24 toolchain, unified).")


        }
    }
}

