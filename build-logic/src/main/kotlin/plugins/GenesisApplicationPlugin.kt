package plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * ===================================================================
 * GENESIS APPLICATION CONVENTION PLUGIN
 * ===================================================================
 *
 * The primary convention plugin for Android application modules.
 *
 * This plugin configures:
 * - Android application plugin and extensions
 * - Kotlin Android support
 * - Hilt dependency injection
 * - KSP annotation processing
 * - Jetpack Compose
 * - Google Services (Firebase)
 * - Consistent build configuration across app modules
 *
 * Plugin Application Order (Critical!):
 * 1. genesis.android.base (inherits JVM/Kotlin settings)
 * 2. com.android.application
 * 3. org.jetbrains.kotlin.android
 * 4. com.google.dagger.hilt.android
 * 5. com.google.devtools.ksp
 * 6. org.jetbrains.kotlin.plugin.compose
 * 7. com.google.gms.google-services
 *
 * @since Genesis Protocol 1.0
 */
class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            // Apply base Genesis conventions first
            apply("genesis.android.base")

            // Apply Android and Kotlin plugins
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("com.google.dagger.hilt.android")
            apply("com.google.devtools.ksp")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("com.google.gms.google-services")
        }

        // Configure Android extension
        val extension = target.extensions.getByType<ApplicationExtension>()
        extension.apply {
            compileSdk = 36

            defaultConfig {
                applicationId = "dev.aurakai.auraframefx"
                minSdk = 34
                targetSdk = 36
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
                isCoreLibraryDesugaringEnabled = true
            }

            buildFeatures {
                compose = true
                buildConfig = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.15"
            }

            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }

        // Set Kotlin JVM target for Android
        target.tasks.withType(KotlinCompile::class.java).configureEach {
            it.kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
}
