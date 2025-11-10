val buildToolsVersion by extra("36.1.0 rc1")
// ===== FORCE MODERN ANNOTATIONS & EXCLUDE OLD ONES =====
plugins {
    id("com.android.application") version "9.0.0-alpha14"
    id("com.android.library") version "9.0.0-alpha14" apply false
    id("com.google.devtools.ksp") version "2.3.0" apply false
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
