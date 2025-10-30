plugins {
    id("com.android.library")


}
android {
    namespace = "dev.aurakai.auraframefx.core.domain"
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

    // Enable Java 25 preview features
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }


    dependencies {
        // Pure business logic, no Android dependencies
        implementation(libs.kotlinx.coroutines.core)
        implementation(project(":core:common"))
        coreLibraryDesugaring(libs.desugar.jdk.libs)
        // Testing
        testImplementation(libs.junit.jupiter.api)
        testRuntimeOnly(libs.junit.jupiter.engine)
        testImplementation(libs.mockk)
    }

}
