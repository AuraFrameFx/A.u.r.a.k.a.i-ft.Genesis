// Define Compose compiler metrics and reports paths
val composeMetricsDir = buildDir.resolve("compose_metrics")
val composeReportsDir = buildDir.resolve("compose_reports")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}


android {
    namespace = "dev.aurakai.auraframefx.auralab"
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
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    buildFeatures {
        // Modern build feature-module
        buildConfig = true
        aidl = false
    }



    dependencies {
        // SACRED RULE #5: DEPENDENCY HIERARCHY
        implementation(project(":core-module"))
        // REMOVED: implementation(project(":app")) - Circular dependency! Libraries should not depend on :app
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.hilt.android)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.compose.material3)
        implementation(libs.bundles.lifecycle)
        implementation(libs.bundles.room)
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.bundles.coroutines)
        implementation(libs.bundles.network)
        implementation(platform(libs.firebase.bom))
        implementation(libs.bundles.firebase)
        ksp(libs.hilt.compiler)
        ksp(libs.androidx.room.compiler)
        implementation(libs.firebase.auth.ktx)

        implementation(libs.androidx.material)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.leakcanary.android)
    }
}
