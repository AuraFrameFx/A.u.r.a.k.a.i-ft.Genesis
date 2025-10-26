plugins {
    id("com.android.library") version "9.0.0-alpha11"
    id("com.google.devtools.ksp") version "2.3.0"

}
android {
    namespace = "dev.aurakai.auraframefx.dataveinoraclenative"
    compileSdk = 36
}
dependencies {
    // Root/hooking dependencies (grouped together at the top)
    implementation(libs.core)
    // Use local jars in project `libs/` folder to resolve Xposed API offline
    compileOnly(files("../libs/api-82.jar"))
    compileOnly(libs.yukihookapi)
    implementation(libs.libsu.io)

    implementation(libs.timber)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.ui.tooling)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.test)
    implementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
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
    implementation(libs.compose.theme.adapter.x)
    implementation(libs.firebase.auth.ktx)
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
    implementation(libs.androidx.material)
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.leakcanary.android)

}
