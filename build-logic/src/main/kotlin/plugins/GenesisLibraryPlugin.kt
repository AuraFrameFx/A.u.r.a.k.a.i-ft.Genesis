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
                compileSdk = 36

                defaultConfig {
                    minSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.9.22"
                }
            }

            // Apply KSP plugin after Android plugin
            apply("com.google.devtools.ksp")
        }
    }
}
