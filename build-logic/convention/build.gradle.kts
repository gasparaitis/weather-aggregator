import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
}

group = "com.gasparaitisj.buildlogic"

java {
    sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
    targetCompatibility = JavaVersion.toVersion(libs.versions.java.get().toInt())
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.compose.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    lintChecks(libs.androidx.lint.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.app.android.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
