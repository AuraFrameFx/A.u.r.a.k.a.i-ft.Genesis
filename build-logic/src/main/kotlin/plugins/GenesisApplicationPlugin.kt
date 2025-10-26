package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ===================================================================
 * GENESIS APPLICATION CONVENTION PLUGIN
 * ===================================================================
 *
 * The primary convention plugin for Android application modules.
 *
 * Plugin Application Order (Critical!):
 * 1. Android Application
 * 2. Hilt (Dependency Injection)
 * 3. KSP (Annotation Processing)
 * 4. Compose Compiler
 * 5. Google Services (Firebase)
 *
 * @since Genesis Protocol 1.0
 */
class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Apply plugins directly to the plugin manager for clarity and compatibility
        // with strict Gradle versions.
        with(target.pluginManager) {
            apply(/* pluginId = */ "com.android.application")
            apply(/* pluginId = */ "com.google.dagger.hilt.android")
            apply(/* pluginId = */ "com.google.devtools.ksp")
            apply(/* pluginId = */ "org.jetbrains.kotlin.plugin.compose")
            apply(/* pluginId = */ "com.google.gms.google-services")
        }
    }
}
