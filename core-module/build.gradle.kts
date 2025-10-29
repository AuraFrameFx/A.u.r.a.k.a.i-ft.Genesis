import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dev.aurakai.auraframefx.coremodule"
    compileSdk = 36
    defaultConfig {
        minSdk = 34
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
// Configure Kotlin compile tasks to set JVM target and add Compose compiler freeCompilerArgs using the new compilerOptions API
    tasks.withType(KotlinJvmCompile::class.java).configureEach {
        (this as KotlinJvmCompile).compilerOptions {
            jvmTarget.set(JvmTarget.JVM_24)
            freeCompilerArgs.addAll(

            )
        }
    }
}

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
}
