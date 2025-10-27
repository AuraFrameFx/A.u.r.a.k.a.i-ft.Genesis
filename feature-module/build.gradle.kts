plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"
}


android {
    namespace = "dev.aurakai.auraframefx.java"
    compileSdk = 36
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}


dependencies {
    implementation("com.github.topjohnwu.libsu:core:6.0.0")
    implementation("com.github.topjohnwu.libsu:io:6.0.0")
    implementation(libs.kotlin.stdlib.jdk8)

    implementation(libs.timber)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.ui.test.junit4)
    implementation(libs.ui.test.manifest)
    implementation(libs.androidx.ui.test)
    implementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
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
    implementation(libs.bundles.network)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.compose.theme.adapter)
    implementation(libs.firebase.auth.ktx)
    compileOnly(files("libs/api-82.jar"))
    compileOnly(files("libs/api-82-sources.jar"))
    implementation(libs.androidx.material)
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.leakcanary.android)

    // Explicit androidx versions requested by the user (added alongside existing libs entries)
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation(platform("androidx.compose:compose-bom:2025.10.01"))
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    implementation("androidx.compose.ui:ui:1.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation("androidx.room:room-runtime:2.8.3")
    implementation("androidx.room:room-ktx:2.8.3")
    implementation("androidx.work:work-runtime-ktx:2.11.0")
    implementation("androidx.hilt:hilt-work:1.3.0")
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation("androidx.datastore:datastore-core:1.1.7")
    implementation("androidx.security:security-crypto:1.1.0")
    androidTestImplementation("androidx.benchmark:benchmark-junit4:1.4.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
}
