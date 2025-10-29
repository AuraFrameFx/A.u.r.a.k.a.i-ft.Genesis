plugins {
    id("genesis.application")
    id("com.android.library")
    alias(libs.plugins.kotlin - android) apply true
    id("com.google.devtools.ksp")
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 36

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx.app"
        minSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }

    buildFeatures {
        compose = true
        aidl = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
        isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes += listOf(
                "META-INF/LICENSE.md",
                "META-INF/gradle/incremental.annotation.processors",
                "META-INF/INDEX.LIST",
                "META-INF/androidx/room/room-compiler-processing/LICENSE.txt"
            )
        }
    }
}

dependencies {
    // Project modules
    implementation(project(":core-module"))
    implementation(project(":feature-module"))
    implementation(project(":romtools"))
    implementation(project(":secure-comm"))
    implementation(project(":collab-canvas"))
    implementation(project(":colorblendr"))
    implementation(project(":datavein-oracle-native"))
    implementation(project(":extendsysa"))
    implementation(project(":extendsysb"))
    implementation(project(":extendsysc"))
    implementation(project(":extendsysd"))
    implementation(project(":extendsyse"))
    implementation(project(":extendsysf"))
    implementation(project(":benchmark"))
    implementation(project(":oracle-drive-integration"))
    implementation(project(":sandbox-ui"))

    // AndroidX Core
    implementation(libs.bundles.androidx.core)
    implementation(libs.androidx.security.crypto)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.bundles.compose.debug)

    // Lifecycle & Architecture
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Kotlin Libraries
    implementation(libs.bundles.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // Networking
    implementation(libs.bundles.network)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Utilities
    implementation(libs.timber)
    implementation(libs.gson)
    implementation(libs.coil.compose)

    // Lottie Animations
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    // HorizontalPager (Accompanist for compatibility)
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")

    // LibSU for root access
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")

    // Hooking Framework
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // Core Library Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.testing.android)

    // Debug
    debugImplementation(libs.leakcanary.android)
}
