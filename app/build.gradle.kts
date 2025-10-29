plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
}

android {

    namespace = "dev.aurakai.auraframefx.app"
    compileSdk = 36
    // Configure Compose compiler plugin outputs/paths (optional but recommended)

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx.app.debug"
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
    // Source sets
    sourceSets {
        getByName("main") {
            java {
                srcDir("src/main/kotlin")
            }
        }
    }

    // Build features
    buildFeatures {
        compose = true
        buildConfig = true
        shaders = false
    }

    // Java compatibility / desugaring
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
        isCoreLibraryDesugaringEnabled = true
    }
}

    dependencies {
        // Core AndroidX
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.lifecycle.runtime.ktx)

        // Hilt
        implementation(libs.hilt.android)
        ksp(libs.hilt.compiler)
        implementation(libs.androidx.hilt.navigation.compose)
        implementation(libs.androidx.hilt.work)

        // Java 25 desugaring
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        // Compose
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        debugImplementation(libs.androidx.compose.ui.tooling)

        // Core and hooking/runtime dependencies
        implementation(libs.libsu.core)
        implementation(libs.libsu.io)
        implementation("com.github.topjohnwu.libsu:core:5.0.4")
        implementation("com.github.topjohnwu.libsu:io:5.0.4")

        // Xposed/YukiHook
        compileOnly(libs.xposed.api)
        compileOnly(libs.yukihook.api)
        compileOnly(files("libs/api-82.jar"))
        compileOnly(files("libs/api-82-sources.jar"))

        // Logging
        implementation(libs.timber)

        // Material Design
        implementation(libs.androidx.material)

        // Project modules
        implementation(project(":core-module"))
        implementation(project(":feature-module"))
        implementation(project(":romtools"))
        implementation(project(":collab-canvas"))
        implementation(project(":colorblendr"))
        implementation(project(":datavein-oracle-native"))
        implementation(project(":oracle-drive-integration"))

        // AndroidX Core
        implementation(libs.bundles.androidx.core)
        implementation(libs.androidx.security.crypto)

        // Lifecycle & Architecture
        implementation(libs.bundles.lifecycle)

        // Room Database
        implementation(libs.bundles.room)
        ksp(libs.androidx.room.compiler)

        // DataStore
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)

        // WorkManager
        implementation(libs.androidx.work.runtime.ktx)

        // Kotlin Libraries
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.bundles.coroutines)

        // Firebase
        implementation(platform(libs.firebase.bom))
        implementation(libs.bundles.firebase)
        implementation(libs.firebase.auth)

        // Testing
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing)
        androidTestImplementation(libs.androidx.benchmark.junit4)
        androidTestImplementation(libs.androidx.test.uiautomator)

        // Debug Tools
        debugImplementation(libs.leakcanary.android)
    }
