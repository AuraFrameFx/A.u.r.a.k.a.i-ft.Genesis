package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * ===================================================================
 * GENESIS LIBRARY HILT CONVENTION PLUGIN
 * ===================================================================
 *
 * Extends Genesis library conventions with Hilt dependency injection.
 *
 * This plugin automatically provides:
 * - genesis.android.library (base library configuration)
 * - Hilt Android plugin
 * - KSP for annotation processing
 * - Hilt runtime and compiler dependencies
 *
 * Usage:
 * ```kotlin
 * plugins {
 *     id("genesis.android.library.hilt")
 * }
 * ```
 *
 * Dependencies auto-provided:
 * - implementation(hilt-android)
 * - ksp(hilt-android-compiler)
 *
 * Plugin Application Order:
 * 1. genesis.android.library (inherits all library conventions)
 * 2. com.google.dagger.hilt.android
 * 3. com.google.devtools.ksp
 *
 * @since Genesis Protocol 1.0
 */
class GenesisLibraryHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base library conventions
            pluginManager.apply("genesis.android.library")

            // Apply Hilt and KSP plugins
            pluginManager.apply("com.google.dagger.hilt.android")
            pluginManager.apply("com.google.devtools.ksp")

            // Auto-provide Hilt dependencies
            val libs = extensions.getByType(org.gradle.api.artifacts.VersionCatalogsExtension::class.java)
                .named("libs")

            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}
