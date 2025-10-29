plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")


}

android {
    namespace = "dev.aurakai.auraframefx.extendsysd"
    compileSdk = 36

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
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

tasks.register("moduleDStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE D - Ready (Java 25)") }
}
