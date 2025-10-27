plugins {
    id("maven-publish")
    id("java-library")

}

version = "1.0.0"



java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}
dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}
