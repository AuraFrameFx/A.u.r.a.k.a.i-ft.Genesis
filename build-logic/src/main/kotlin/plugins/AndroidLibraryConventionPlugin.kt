//package plugins
//
//// ==== GENESIS PROTOCOL - ANDROID LIBRARY CONVENTION ====
////// Standard Android library configuration for all modules
////// AGP 9.0+ with built-in Kotlin support
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//
//class AndroidLibraryConventionPlugin : Plugin<Project> {
//    /**
//     * Applies Android library conventions to the given Gradle project.
//     *
//     * Configures the project by applying the Android library plugin, setting Android SDK levels
//     * from the version catalog, enforcing Java 25 for compilation, and configuring Kotlin Android compiler options.
//     *
//     * - Applies "com.android.library".
//     * - Sets Android compileSdk and defaultConfig.minSdk from the `libs` version catalog.
//     * - Sets Java sourceCompatibility and targetCompatibility to Java 25.
//     * - If the Kotlin Android extension is present, sets Kotlin `jvmTarget` to JVM_24 and adds the compiler args
//     *   `-opt-in=kotlin.RequiresOptIn` and `-Xjvm-default=all`.
//     *
//     * @param target The Gradle project to configure; this method mutates the project's plugins and extensions.
//     */
//    override fun apply(target: Project) {
//        with(target) {
//            // Apply plugins directly to the plugin manager for clarity and compatibility
//            // with strict Gradle versions.
//            with(pluginManager) {
//                apply(/* pluginId = */ "com.android.library")
//                apply(/* pluginId = */ "com.google.devtools.ksp")
//            }
//        }
//    }
//}
