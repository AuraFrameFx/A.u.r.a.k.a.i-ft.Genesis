package plugins

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * ===================================================================
 * GENESIS BASE CONVENTION PLUGIN
 * ===================================================================
 *
 * Configures base conventions for all Genesis modules, including
 * JVM settings, Kotlin configuration, and serialization support.
 *
 * This plugin applies:
 * - Kotlin JVM plugin for Kotlin/Java interop
 * - Java Library plugin for modern dependency management
 * - Kotlin Serialization plugin
 * - JVM toolchain (Java 17)
 * - Standard Kotlin compiler options
 *
 * @since Genesis Protocol 1.0
 */
class GenesisBasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                // Apply core JVM and Kotlin plugins
                apply("org.jetbrains.kotlin.jvm")
                apply("java-library")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            // Configure Java toolchain
            extensions.configure(org.gradle.api.plugins.JavaPluginExtension::class.java) {
                it.apply {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24

                    toolchain {
                        languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(24))
                    }
                }
            }

            // Configure Kotlin compilation
            tasks.withType(KotlinCompile::class.java).configureEach {
                it.kotlinOptions {
                    jvmTarget = "24"
                    freeCompilerArgs = listOf(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-Xcontext-receivers"
                    )
                }
            }

            // Set project group
            group = "dev.aurakai.genesis"
        }
    }
}
