plugins {
    id("com.android.test")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.creative.isitvegan.testing.ui"
    compileSdk = 36
    targetProjectPath = ":app"

    defaultConfig {
        minSdk = 24
        targetSdk = 35
        testInstrumentationRunner = "com.creative.isitvegan.HiltTestRunner"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":testing:engine"))
    
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.material3)
    implementation(libs.hilt.android)
    implementation(libs.hilt.testing)
    ksp(libs.hilt.compiler)
    
    // We need to include app dependencies that the robots might need
    // or just rely on the fact that targetProjectPath is set.
}
