package plugins

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Genesis Library Plugin
 * Configures Android library modules with Genesis conventions
 */
class GenesisLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            // Apply Android Library plugin
            apply("com.android.library")

            // Apply other required plugins
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.serialization")
            apply("com.google.devtools.ksp")
            apply("com.google.dagger.hilt.android")

            // Configure Android extension
            target.extensions.getByType<LibraryExtension>().apply {
                // Add any library-specific configuration here
                compileSdk = 34

                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
            }

            // Apply KSP plugin after Android plugin
            apply("com.google.devtools.ksp")
        }
    }
}
