package plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * ===================================================================
 * GENESIS APPLICATION CONVENTION PLUGIN
 * ===================================================================
 *
 * The primary convention plugin for Android application modules.
 *
 * Plugin Application Order (Critical!):
 * 1. Android Application
 * 2. KSP (Annotation Processing)
 * 3. Hilt (Dependency Injection)
 * 4. Compose Compiler
 * 5. Google Services (Firebase)
 *
 * @since Genesis Protocol 1.0
 */
class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            // 1. Apply Android Application plugin
            apply("com.android.application")

            // 2. Apply Kotlin Android plugin
            apply("org.jetbrains.kotlin.android")

            // 3. Apply KSP plugin (Annotation Processing)
            apply("com.google.devtools.ksp")

            // 4. Apply Hilt (Dependency Injection)
            apply("com.google.dagger.hilt.android")

            // 5. Apply Compose Compiler
            apply("org.jetbrains.kotlin.plugin.compose")

            // 6. Apply Google Services (Firebase)
            apply("com.google.gms.google-services")

            // Configure Android extension
            target.extensions.getByType<ApplicationExtension>().apply {
                compileSdk = 34

                defaultConfig {
                    applicationId = "dev.aurakai.auraframefx"
                    minSdk = 24
                    targetSdk = 34
                    versionCode = 1
                    versionName = "1.0"

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
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

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.5.8"
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}
