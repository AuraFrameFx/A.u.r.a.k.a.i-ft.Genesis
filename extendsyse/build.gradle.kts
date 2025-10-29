plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")


}

android {
    namespace = "dev.aurakai.auraframefx.extendsyse"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
    }
    ndkVersion = "29.0.14206865"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Module dependencies
    implementation(project(":core-module"))

    // Core Android
    implementation(libs.androidx.core.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.register("moduleEStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE E - Ready (Java 25)") }
}
