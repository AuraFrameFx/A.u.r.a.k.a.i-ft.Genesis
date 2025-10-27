plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("org.jetbrains.kotlin.jvm")
}

android {
    namespace = "dev.aurakai.auraframefx.core.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    kotlinOptions {
        jvmTarget = "25"
    }
}

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    dependencies {
        // Pure business logic, no Android dependencies
        implementation(libs.kotlinx.coroutines.core)
        implementation(project(":core:common"))
        implementation(libs.kotlin.stdlib.jdk8)

        // Testing
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
    }

tasks.test {
    useJUnitPlatform()
}
