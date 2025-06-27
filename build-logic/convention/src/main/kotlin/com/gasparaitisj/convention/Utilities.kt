package com.gasparaitisj.convention

import java.io.BufferedReader
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.support.useToRun
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val Project.gitBuildType: String
    get() {
        val branch = executeCommand("git", "rev-parse", "--abbrev-ref", "HEAD")
        return if (branch == "main") "Release" else "QA"
    }

val Project.gitCommitCount: Int get() =
    executeCommand("git", "rev-list", "--count", "HEAD").toInt()

val Project.libs: VersionCatalog
    get() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")

val Project.projectJavaVersion get() = libs.projectJavaVersion

val Project.projectJvmTarget get() = libs.projectJvmTarget

val VersionCatalog.projectJavaVersion
    get() = JavaVersion.toVersion(findVersion("java").get().toString().toInt())

val VersionCatalog.projectJvmTarget
    get() = JvmTarget.fromTarget(findVersion("java").get().toString())

internal fun executeCommand(vararg args: String): String {
    Runtime
        .getRuntime()
        .exec(args)
        .run {
            waitFor()
            val output = inputStream.useToRun { bufferedReader().use(BufferedReader::readText) }
            destroy()
            return output.trim()
        }
}
