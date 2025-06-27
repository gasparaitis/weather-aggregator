import com.gasparaitisj.convention.gitBuildType
import com.gasparaitisj.convention.gitCommitCount

internal val packageName = "com.gasparaitisj.weatheraggregator"

plugins {
    alias(libs.plugins.app.android.application)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = packageName
    defaultConfig {
        applicationId = packageName
        versionCode = gitCommitCount
        versionName = "1.0.0"
    }
    buildTypes {
        all {
            versionNameSuffix = "-$gitCommitCount:$gitBuildType"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.google.oss.licenses)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

