val buildToolsVersion by extra("36.1.0-rc1")
// ===== FORCE MODERN ANNOTATIONS & EXCLUDE OLD ONES =====
plugins {
    // Core Android and Kotlin plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // Kotlin plugins
    kotlin("android") version "2.3.0-Beta2" apply false
    kotlin("plugin.serialization") version "2.3.0-Beta2" apply false

    // Compose - using direct plugin ID for AGP 9.0.0-alpha10 compatibility
    id("org.jetbrains.compose") version "1.6.0-beta02" apply false

    // Dagger Hilt
    id("com.google.dagger.hilt.android") version "2.51" apply false

    // KSP - using version from version catalog
    id("com.google.devtools.ksp") version "2.3.0" apply false

    // Tooling
    alias(libs.plugins.foojay.resolver) apply false
}

subprojects {
    configurations.all {
        resolutionStrategy {
            // Force the modern JetBrains annotations version
            force("org.jetbrains:annotations:26.0.2-1")
            // Prefer org.jetbrains over com.intellij for annotations
            eachDependency {
                if (requested.group == "com.intellij" && requested.name == "annotations") {
                    useTarget("org.jetbrains:annotations:26.0.2-1")
                    because("Avoid duplicate annotations classes")
                }
            }
        }
        // Exclude the old IntelliJ annotations from all dependencies
        exclude(group = "com.intellij", module = "annotations")
    }
}
