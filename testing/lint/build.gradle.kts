plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:32.2.1")
    compileOnly("com.android.tools.lint:lint-checks:32.2.1")
    testImplementation("com.android.tools.lint:lint-tests:32.2.1")
}

tasks.jar {
    manifest {
        attributes("Lint-Registry-v2" to "com.creative.isitvegan.lint.TestEngineRegistry")
    }
}
