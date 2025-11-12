package plugins

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * ===================================================================
 * GENESIS LIBRARY CONVENTION PLUGIN
 * ===================================================================
 *
 * Configures Android library modules with Genesis conventions.
 *
 * This plugin configures:
 * - Android library plugin and extensions
 * - Kotlin Android support
 * - Jetpack Compose support
 * - Kotlin Serialization (from base)
 * - Consistent build configuration across library modules
 *
 * NOTE: This plugin does NOT include Hilt or KSP.
 * For libraries requiring dependency injection, use genesis.android.library.hilt
 *
 * Plugin Application Order:
 * 1. genesis.android.base (inherits JVM/Kotlin settings)
 * 2. com.android.library
 * 3. org.jetbrains.kotlin.android
 * 4. org.jetbrains.kotlin.plugin.compose
 *
 * @since Genesis Protocol 1.0
 */
class GenesisLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            // Apply base Genesis conventions first
            apply("genesis.android.base")

            // Apply Android library and Kotlin plugins
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        // Configure Android extension for library modules
        val extension = target.extensions.getByType<LibraryExtension>()
        extension.apply {
            compileSdk = 36

            defaultConfig {
                minSdk = 34
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    // Note: Libraries use 'consumerProguardFiles', not 'proguardFiles'
                    consumerProguardFiles("consumer-rules.pro")
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
                isCoreLibraryDesugaringEnabled = true
            }

            buildFeatures {
                compose = true
                buildConfig = true
                aidl = false
                shaders = false
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
                jvmTarget = JavaVersion.VERSION_24.toString()
            }
        }
    }
}
