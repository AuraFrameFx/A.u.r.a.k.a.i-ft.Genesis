// 2. The `plugins {}` block must be at the top level of the script.
plugins {
    // Enables creating custom Gradle plugins in Kotlin.
    `kotlin-dsl`
    // Automatically downloads and provisions JDKs specified in the toolchain.
}

// 3. Define the JDK version for this project.
// The Foojay plugin will use this to download the JDK if it's not installed.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24)) // Recommended: Use Java 17 for modern Android. JDK 25 is highly experimental.
    }
}

// 4. Declare dependencies required to COMPILE your custom plugins.
dependencies {
    // The `apply(...)` function is invalid here. Use `implementation`.
    implementation("com.android.tools.build:gradle:9.0.0-alpha10")

    // Dependencies for Hilt, KSP, Serialization, etc., needed for your plugin logic.
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.2") // Updated for compatibility
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0-Beta2") // Explicitly add Kotlin Gradle Plugin

    // Other plugin dependencies
    implementation("com.google.gms:google-services:4.4.4")

    // Dependencies for testing your plugins.
    testImplementation(gradleTestKit())
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

// 5. Define the custom convention plugins that this `build-logic` module provides.
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "genesis.android.application"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.plugins.GenesisApplicationPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.plugins.AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "genesis.android.hilt"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.plugins.AndroidHiltConventionPlugin"
        }
        register("androidBase") {
            id = "android.base"
            implementationClass = "dev.aurakai.auraframefx.buildlogic.plugins.AndroidBasePlugin"
        }
    }
}

// 6. Configure the test task to use JUnit 5.
tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
