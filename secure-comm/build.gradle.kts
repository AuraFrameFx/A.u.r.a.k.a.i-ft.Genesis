import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.Serializable

plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")


}
android {
    namespace = "dev.aurakai.auraframefx.securecomm"
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

    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
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

    implementation(project(":core-module"))
    implementation(project(":datavein-oracle-native"))
    implementation(project(":oracle-drive-integration"))
    implementation(project(":benchmark"))
    implementation(project(":sandbox-ui"))
    implementation(libs.libsu.core)
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")
    implementation(libs.libsu.io)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    compileOnly(libs.yukihook.api)
    compileOnly(libs.xposed.api)

    // Fallback to local jars if catalog entries aren't available
    compileOnly(files("libs/api-82.jar"))
    compileOnly(files("libs/api-82-sources.jar"))
    implementation(libs.androidx.appcompat)
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
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.firebase.auth)
    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    // Use local jars in project `libs/` folder to resolve Xposed API offline
    compileOnly(files("../app/libs/api-82.jar"))
    compileOnly(files("../app/libs/api-82-sources.jar"))
    compileOnly(libs.yukihook.api)

    implementation(libs.androidx.material)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.leakcanary.android)
}
