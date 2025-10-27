plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.core.module"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

}

version = "1.0.0"
android {
    namespace = "dev.aurakai.auraframefx.collab_canvas"
    compileSdk = 36
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(25)) }
    }

    dependencies {

        // Module dependency
        api(project(":list"))

        // Concurrency and serialization
        implementation(libs.bundles.coroutines)
        implementation(libs.kotlinx.serialization.json)

        // File operations and compression
        implementation(libs.commons.io)
        implementation(libs.commons.compress)
        implementation(libs.xz)

        // Logging API only (do not bind implementation at runtime for libraries)
        implementation(libs.slf4j.api)

        // Testing (JUnit 5)
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.21")
    }
