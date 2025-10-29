plugins {
    id("com.android.application")
    // Hilt and KSP are applied without `apply false` in the module
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp) // Correct position: Apply KSP before Hilt
    alias(libs.plugins.hilt)

}

android {

    namespace = "dev.aurakai.auraframefx.app"
    compileSdk = 36
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
    compileOptions {
        sourceSets {
            getByName("main") {
                java {
                    srcDir("src/main/kotlin")
                }
            }
        }
        buildFeatures {
            compose = true
            buildConfig = true
            aidl = true
            shaders = false
        }

        // Java compatibility / desugaring
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_25
            targetCompatibility = JavaVersion.VERSION_25
            isCoreLibraryDesugaringEnabled = true
        }


        composeOptions {
            kotlinCompilerExtensionVersion = "2.3.0-beta2"
        }

        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }
        }

        ndkVersion = "29.0.14206865"
    }

}

    dependencies {
        // Core and hooking/runtime dependencies (required per project conventions)
        implementation(libs.libsu.core)
        implementation("com.github.topjohnwu.libsu:core:5.0.4")
        implementation("com.github.topjohnwu.libsu:io:5.0.4")
        implementation(libs.libsu.io)
        implementation("org.google.dagger.hilt.android:2.57.2")
        // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
        compileOnly(libs.xposed.api)
        compileOnly(libs.yukihook.api)

        // Fallback to local jars if catalog entries aren't available
        compileOnly(files("libs/api-82.jar"))
        compileOnly(files("libs/api-82-sources.jar"))

        implementation(libs.timber)
        implementation(libs.hilt.android)
        implementation(libs.androidx.material)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.ui.tooling)

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
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        // Compose
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.bundles.compose.ui)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.compose.ui)


        // Lifecycle & Architecture
        implementation(libs.bundles.lifecycle)

        // Room Database
        implementation(libs.bundles.room)
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
