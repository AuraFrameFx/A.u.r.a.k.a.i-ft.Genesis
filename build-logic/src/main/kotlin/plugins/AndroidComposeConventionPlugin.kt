//package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

/** / * Android Compose Convention Plugin / * Configures Jetpack Compose for Android modules
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.10"
            }
        }

        extensions.configure<KotlinProjectExtension> {
            // Compose compiler configuration
        }
    }
}

