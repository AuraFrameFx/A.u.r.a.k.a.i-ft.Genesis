plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "2.3.0"

}

android {

    namespace = "dev.aurakai.auraframefx.app"
    compileSdk = 36
    defaultConfig {
        applicationId = "dev.aurakai.auraframefx.app"
        minSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        lint {
            baseline = file("lint-baseline.xml")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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


    ndkVersion = "29.0.14206865"
}

dependencies {
    // Core and hooking/runtime dependencies (required per project conventions)
    implementation(libs.core)
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")
    implementation(libs.libsu.io)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    compileOnly(libs.yukihookapi)
    // Fallback to local jars if catalog entries aren't available
    compileOnly(files("libs/api-82.jar"))
    compileOnly(files("libs/api-82-sources.jar"))

    implementation(libs.timber)
    implementation(libs.hilt.android)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.test)
    implementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":core-module"))
    implementation(project(":feature-module"))
    implementation(project(":romtools"))
    implementation(project(":collab-canvas"))
    implementation(project(":colorblendr"))
    implementation(project(":datavein-oracle-native"))
    implementation(project(":oracle-drive-integration"))

    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.security.crypto)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.compose.theme.adapter.x)
    // Compose Material & Icons
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")

    // Lifecycle & Architecture
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Room Database
    implementation(libs.bundles.room)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // WorkManager & Hilt Integration
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.navigation.compose)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Kotlin Libraries
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Networking
    implementation(libs.bundles.network)
    // Add Moshi explicitly for DI resolution
    implementation(libs.moshi)
    implementation(libs.converter.moshi)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.auth.ktx)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.test.uiautomator)

    // Debug Tools
    debugImplementation(libs.leakcanary.android)
}
