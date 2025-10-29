import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
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
    // Configure Kotlin compile tasks to set JVM target and add Compose compiler freeCompilerArgs using the new compilerOptions API
    tasks.withType(KotlinJvmCompile::class.java).configureEach {
        (this as KotlinJvmCompile).compilerOptions {
            jvmTarget.set(JvmTarget.JVM_24)


            dependencies {
                // Core
                implementation(project(":core-module"))
                implementation(libs.androidx.core.ktx)
                implementation(libs.bundles.lifecycle)
                implementation("com.github.topjohnwu.libsu:core:6.0.0")
                implementation("com.github.topjohnwu.libsu:io:6.0.0")
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
        }
    }
}
