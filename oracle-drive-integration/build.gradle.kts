plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.oracledriveintegration"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        multiDexEnabled = true  // Required for core library desugaring
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

    dependencies {
        implementation("com.github.topjohnwu.libsu:core:6.0.0")
        implementation("com.github.topjohnwu.libsu:io:6.0.0")
        coreLibraryDesugaring(libs.desugar.jdk.libs)

        implementation(libs.timber)
        implementation(libs.hilt.android)
        ksp(libs.hilt.android.compiler)
        implementation(libs.androidx.material)

        implementation(libs.hilt.android)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
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
        implementation(libs.compose.theme.adapter)
        implementation(libs.firebase.auth.ktx)
        compileOnly(files("libs/api-82.jar"))
        compileOnly(files("libs/api-82-sources.jar"))
        implementation(libs.androidx.material)
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing)
        debugImplementation(libs.leakcanary.android)
        implementation(libs.kotlin.stdlib.jdk8)
    }
}
