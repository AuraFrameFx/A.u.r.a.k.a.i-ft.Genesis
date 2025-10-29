plugins {
    id("com.android.library")
    // Hilt and KSP are applied without `apply false` in the module
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp) // Correct position: Apply KSP before Hilt
    alias(libs.plugins.hilt)

}

android {
    namespace = "dev.aurakai.auraframefx.romtools"
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
        aidl = true
        shaders = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

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
    }
}

dependencies {
    // Core and hooking/runtime dependencies (ordered per project conventions)
    implementation(libs.libsu.core)
    implementation(libs.androidx.appcompat) // ensured present near top as requested

    // TopJohnWu libsu runtime helpers (required by modules that perform system/root ops)
    implementation("com.github.topjohnwu.libsu:core:6.0.0")
    implementation("com.github.topjohnwu.libsu:io:6.0.0")
    implementation(libs.libsu.io)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    compileOnly(libs.yukihook.api)
    compileOnly(libs.xposed.api)

    // Fallback to local jars if catalog entries aren't available
    compileOnly(files("libs/api-82.jar"))
    compileOnly(files("libs/api-82-sources.jar"))

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    // Use local jars in project `libs/` folder to resolve Xposed API offline
    implementation(libs.timber)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.tooling)

    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.firebase.auth)

    // Xposed/YukiHook Framework (ROM tools need system-level hooks)
    compileOnly(libs.xposed.api)            // Traditional Xposed API v82
    compileOnly(libs.yukihook.api)
    // Testing
    testImplementation(libs.junit.jupiter.api)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.test.uiautomator)
}
