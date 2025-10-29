package conventionclass

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Convention plugin for Android Application modules.
 * Applies the base application plugin and configures common settings.
 */
class GenesisApplicationConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("genesis.android.application")
            // Apply any application-specific conventions here
        }
    }
}
