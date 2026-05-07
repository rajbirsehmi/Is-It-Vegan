
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    id("com.android.lint") version "9.2.1" apply false
}

subprojects {
    plugins.withId("com.android.application") {
        extensions.configure<com.android.build.api.dsl.ApplicationExtension>("android") {
            lint {
                checkDependencies = true
                abortOnError = true
                lintConfig = file("${rootProject.projectDir}/lint.xml")
            }
        }
    }

    plugins.withId("com.android.library") {
        extensions.configure<com.android.build.api.dsl.LibraryExtension>("android") {
            lint {
                checkDependencies = true
                abortOnError = true
                lintConfig = file("${rootProject.projectDir}/lint.xml")
            }
        }
    }

    plugins.withId("com.android.test") {
        extensions.configure<com.android.build.api.dsl.TestExtension>("android") {
            lint {
                checkDependencies = true
                abortOnError = true
                lintConfig = file("${rootProject.projectDir}/lint.xml")
            }
        }
    }

    afterEvaluate {
        val isAndroid = plugins.hasPlugin("com.android.application") ||
                plugins.hasPlugin("com.android.library") ||
                plugins.hasPlugin("com.android.test")

        if (!isAndroid && (plugins.hasPlugin("java") || plugins.hasPlugin("java-library") || plugins.hasPlugin("org.jetbrains.kotlin.jvm"))) {
            apply(plugin = "com.android.lint")
            extensions.configure<com.android.build.api.dsl.Lint>("lint") {
                checkDependencies = true
                abortOnError = true
                lintConfig = file("${rootProject.projectDir}/lint.xml")
            }
        }
    }
}