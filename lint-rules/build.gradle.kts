plugins {
    id("kotlin")
}

dependencies {
    compileOnly(libs.lint.api)
    compileOnly(libs.lint.checks)
    
    testImplementation(libs.lint.tests)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

tasks.jar {
    manifest {
        attributes("Lint-Registry-V2" to "com.creative.isitvegan.lint.TestLocationIssueRegistry")
    }
}