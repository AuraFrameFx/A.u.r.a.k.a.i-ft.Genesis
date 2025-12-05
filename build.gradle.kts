val buildToolsVersion by extra("36.1.0 rc1")
// ===== FORCE MODERN ANNOTATIONS & EXCLUDE OLD ONES =====
plugins {
    id("com.android.application") version "9.0.0-alpha11"
    id("com.android.library") version "9.0.0-alpha11" apply false
    id("com.google.devtools.ksp") version "2.3.0" apply false

    // Kotlin plugins - centrally registered to prevent duplication in subprojects
    id("org.jetbrains.kotlin.android") version "2.3.0-Beta2" apply false
    id("org.jetbrains.kotlin.jvm") version "2.3.0-Beta2" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.0-Beta2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0-Beta2" apply false

    // Dependency Injection & Google Services
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.gms.google-services") version "4.4.4" apply false
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
