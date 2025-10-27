plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.core.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }


    dependencies {
        // Expose domain layer
        api(project(":core:common"))


        // Data layer dependencies
        implementation(libs.bundles.room)
        implementation(libs.bundles.network)
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)

        // Common utilities
        implementation(project(":core:common"))

        // Testing
        testImplementation(libs.bundles.testing.unit)
    }
}
