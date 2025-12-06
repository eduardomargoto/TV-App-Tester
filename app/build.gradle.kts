import org.jetbrains.kotlin.konan.properties.loadProperties
import kotlin.toString

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    val localProperties = loadProperties(rootProject.file("local.properties").path)
    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file(localProperties["debugkeystore.dir"].toString())
            storePassword = localProperties["debugkeystore.password"].toString()
            keyAlias = localProperties["debugkeystore.alias"].toString()
            keyPassword = localProperties["debugkeystore.keypassword"].toString()
        }
    }
    namespace = "com.apptester.tv"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.apptester.tv"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "SERVER_CLIENT_ID", "\"${localProperties["oauth.webclientid"]}\"")
            buildConfigField("String", "CLIENT_SECRET", "\"${localProperties["oauth.clientsecret"]}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(libs.jetbrains.kotlinx.serialization)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.navigation)

    implementation(libs.coroutines)
    implementation(libs.coroutines.android)

    implementation(libs.identify)
    implementation(libs.bundles.credentials)
    implementation(libs.play.services.auth)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)

    implementation(libs.timber)
    implementation(libs.bundles.datastore)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}