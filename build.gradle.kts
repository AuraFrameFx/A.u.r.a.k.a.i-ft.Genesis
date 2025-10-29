val buildToolsVersion by extra("36.1.0 rc1")
// ===== FORCE MODERN ANNOTATIONS & EXCLUDE OLD ONES =====
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.foojay.resolver) apply false
    // Make kotlin-android available to subprojects via the version catalog (accessor form)
    alias(libs.plugins.kotlin.android) apply false
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
