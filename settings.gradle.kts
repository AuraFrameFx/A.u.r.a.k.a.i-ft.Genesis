// Version: 2025.09.02-03 - Full Enhancement Suite

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    // Include build-logic for convention plugins
    plugins {
        id("build-logic") version "1.0.0"
        id("foojay.build-logic") version "1.0.0"
    }


    repositories {
        // Primary repositories - Google Maven must be first for Hilt
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

        // AndroidX Snapshots
        maven {
            url = uri("https://androidx.dev/kmp/builds/11950322/artifacts/snapshots/repository")
            name = "AndroidX Snapshot"
        }


        // Gradle releases (for org.gradle artifacts like gradle-tooling-api)
        maven {
            url = uri("https://repo.gradle.org/gradle/libs-releases")
            name = "Gradle Releases"
        }
        // AndroidX Compose
        maven {
            url = uri("https://androidx.dev/storage/compose-compiler/repository/")
            name = "AndroidX Compose"
            content {
                includeGroup("androidx.compose.compiler")
            }
        }

    }
}


dependencyResolutionManagement {
    // Enforce consistent dependency resolution
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Repository configuration with all necessary sources
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

        // AndroidX Snapshots
        maven {
            url = uri("https://androidx.dev/kmp/builds/11950322/artifacts/snapshots/repository")
            name = "AndroidX Snapshot"
        }

        // Gradle releases (for org.gradle artifacts like gradle-tooling-api)
        maven {
            url = uri("https://repo.gradle.org/gradle/libs-releases")
            name = "Gradle Releases"
        }

        // AndroidX Compose
        maven {
            url = uri("https://androidx.dev/storage/compose-compiler/repository/")
            name = "AndroidX Compose"
        }
    }
}

rootProject.name = "AuraKai"

// ===== MODULE INCLUSION =====
// Core modules
include(":app")
include(":core-module")
include(":core:common")
include(":core:domain")
include(":core:data")
include(":core:ui")
includeBuild("build-logic")

// Feature modules
include(":feature-module")
include(":datavein-oracle-native")
include(":oracle-drive-integration")
include(":secure-comm")
include(":auralab")
include(":collab-canvas")
include(":chromacore")

// Dynamic modules (A-F)
include(":extendsysa")
include(":extendsysb")
include(":extendsysc")
include(":extendsysd")
include(":extendsyse")
include(":extendsysf")

// Testing & Quality modules
include(":benchmark")
include(":protocore")
include(":list")
println("🏗️ Genesis Protocol Enhanced Build System")
println("📦 Total modules: ${rootProject.children.size}")
println("🎯 Build-logic: Convention plugins active")
println("🧠 Ready to build consciousness substrate!")
