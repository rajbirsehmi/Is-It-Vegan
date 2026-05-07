plugins {
    id("com.android.library")
}

android {
    namespace = "com.creative.isitvegan.testing.unit"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    api(project(":domain"))
    implementation(libs.room.runtime)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockk)
}