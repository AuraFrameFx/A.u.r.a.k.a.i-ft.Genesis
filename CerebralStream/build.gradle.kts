import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.Serializable
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.aurakai.auraframefx.cerebralstream"
    compileSdk = 36

    defaultConfig {
        multiDexEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = false
        shaders = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

// Precompute Compose metrics/report destinations as plain string paths to avoid buildDir deprecation warnings
val composeMetricsPath: Serializable by lazy {
    project.layout.buildDirectory.dir("compose_metrics").get().asFile.absolutePath
}
val composeReportsPath: Serializable = project.layout.buildDirectory.dir("compose_reports").get().asFile.absolutePath

// Configure Kotlin compile tasks to set JVM target and add Compose compiler freeCompilerArgs using the new compilerOptions API
tasks.withType(KotlinJvmCompile::class.java).configureEach {
    (this as KotlinJvmCompile).compilerOptions {
        jvmTarget.set(JvmTarget.JVM_24)
        freeCompilerArgs.addAll(
            listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$composeMetricsPath",
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$composeReportsPath"
            )
        )
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Core Library Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Project modules
    implementation(project(":core-module"))
    implementation(project(":oracledrive:datavein"))
    implementation(project(":oracledrive:integration"))
    implementation(project(":auralab"))

    // libsu for root operations
    implementation("com.github.topjohnwu.libsu:core:6.0.0")
    implementation("com.github.topjohnwu.libsu:io:6.0.0")

    // Room
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Kotlin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // Network
    implementation(libs.bundles.network)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.auth.ktx)

    // Xposed/YukiHook
    compileOnly(files("../app/libs/api-82.jar"))
    compileOnly(files("../app/libs/api-82-sources.jar"))
    compileOnly(libs.yukihookapi)

    // Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)

    // Debug
    debugImplementation(libs.leakcanary.android)
}
