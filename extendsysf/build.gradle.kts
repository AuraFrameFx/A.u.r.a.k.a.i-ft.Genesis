plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}

android {
    namespace = "dev.aurakai.auraframefx.extendsysf"

    compileSdk = 36

    defaultConfig {
        minSdk = 33
    }

    ndkVersion = "29.0.14206865"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}

tasks.register("moduleFStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE F - Ready (Java 25)") }
}


