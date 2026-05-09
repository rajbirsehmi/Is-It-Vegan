plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.creative.isitvegan.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // api ensures modules depending on :core-engine get these automatically
    api(libs.androidx.compose.ui.test.junit4)
    api(libs.androidx.espresso.core)
    api(libs.androidx.test.uiautomator)
    api(libs.hilt.testing)
    api(libs.junit)
}