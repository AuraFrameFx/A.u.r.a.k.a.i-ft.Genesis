plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}
android {
    namespace = "dev.aurakai.auraframefx.colorblendr"
    compileSdk = 36

    defaultConfig {
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}
dependencies {
    // Core
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")
    // Compose
    implementation(platform(libs.androidx.compose.bom))

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Utilities
    implementation(libs.timber)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    // Use local jars in project `libs/` folder to resolve Xposed API offline
    compileOnly(files("../app/libs/api-82.jar"))
    compileOnly(files("../app/libs/api-82-sources.jar"))
    compileOnly(libs.yukihookapi)

    // Testing
    androidTestImplementation(libs.bundles.testing.android)

}
