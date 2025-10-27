// ⚠️ PLACEHOLDER MODULE - NO IMPLEMENTATION YET ⚠️
//
// This module is reserved for future Oracle Cloud Integration features.
// See README.md for planned features and architecture.
//
// Status: Awaiting implementation (0 source files)
// Documented: Yes (see README.md)
// Remove this module if not implementing soon to reduce build time.

plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.oracledriveintegration"
    compileSdk = 36
}

// Dependencies configured for future implementation
dependencies {
    // Core Android & Kotlin
    implementation(libs.androidx.core.ktx)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Compose (for future UI)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room (for local caching)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // DataStore (for settings)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Kotlin libraries
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Network (for cloud API)
    implementation(libs.bundles.network)

    // Logging
    implementation(libs.timber)

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)

    // Debug tools
    debugImplementation(libs.leakcanary.android)
}
