plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.extendsysb"
    compileSdk = 36

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
    // Project dependencies
    implementation(project(":core-module"))

    // Core Android & Kotlin
    implementation(libs.androidx.core.ktx)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation("com.google.android.material:compose-theme-adapter-3:1.1.1")

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Kotlin libraries
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Network
    implementation(libs.bundles.network)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // LibSu (Root utilities)
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")

    // Logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Material Design
    implementation(libs.androidx.material)

    // Xposed API (System extension module needs hooking capabilities)
    compileOnly(libs.yukihookapi)           // YukiHook API v1.3.1
    compileOnly(libs.lsposed.api)           // LSPosed API v6.4
    compileOnly(libs.xposed.api)            // Traditional Xposed API v82

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)

    // Debug tools
    debugImplementation(libs.leakcanary.android)
}

tasks.register("moduleBStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE B - Ready (Java 25)") }
}
