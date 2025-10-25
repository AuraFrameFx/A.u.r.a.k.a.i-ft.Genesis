

// ===== FORCE MODERN ANNOTATIONS & EXCLUDE OLD ONES =====
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
