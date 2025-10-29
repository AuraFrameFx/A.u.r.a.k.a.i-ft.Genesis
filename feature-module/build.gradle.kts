plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")


}

android {
    namespace = "dev.aurakai.auraframefx.java"
    compileSdk = 36
    defaultConfig {
        minSdk = 34
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = true
        shaders = false
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Java compatibility / desugaring
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
        isCoreLibraryDesugaringEnabled = true
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "2.3.0-beta1"
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}


dependencies {
    implementation(libs.libsu.core)
    implementation("com.github.topjohnwu.libsu:core:5.0.4")
    implementation("com.github.topjohnwu.libsu:io:5.0.4")
    implementation(libs.libsu.io)

    // Hooking/runtime-only compile-time APIs for modules that interact with Xposed/YukiHook
    compileOnly(libs.yukihook.api)
    compileOnly(libs.xposed.api)

    // Fallback to local jars if catalog entries aren't available
    compileOnly(files("libs/api-82.jar"))
    compileOnly(files("libs/api-82-sources.jar"))
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.google.dagger:hilt-android:2.57.2")
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.4")
    implementation("androidx.compose.ui:ui-tooling:1.9.4")
    implementation("androidx.compose.ui:ui-test-junit4:1.9.4")
    implementation("androidx.compose.ui:ui-test-manifest:1.9.4")
    implementation("androidx.compose.ui:ui-test:1.9.4")
    implementation("androidx.compose.ui:ui-test-junit4:1.9.4")
    implementation("androidx.compose.ui:ui-test-manifest:1.9.4")
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    implementation("com.google.android.material:compose-theme-adapter-3:1.1.1")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
    compileOnly(files("../app/libs/api-82.jar"))
    compileOnly(files("../app/libs/api-82-sources.jar"))
    implementation(libs.androidx.material)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.leakcanary.android)

    // Explicit androidx versions requested by the user (added alongside existing libs entries)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.security.crypto)
    androidTestImplementation(libs.androidx.benchmark.junit4)
    androidTestImplementation(libs.androidx.test.uiautomator)
}
