import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    //alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.example.composelocationweather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composelocationweather"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        /*val properties = Properties().apply { load(FileInputStream(File(rootProject.rootDir, "local.properties"))) }
        val weatherApiKey = properties.getProperty("WEATHER_API_KEY")
        buildConfigField("String", "WEATHER_API_KEY",weatherApiKey)*/

        //Replace weatherApiKey with your own WEATHER_API_KEY
        val weatherApiKey = System.getenv("WEATHER_API_KEY") ?: ""
        buildConfigField("String", "WEATHER_API_KEY", "\"${weatherApiKey}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.kotlinx.serialization.json)

    //Network
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    //Room
    implementation(libs.room)
    implementation(libs.room.extension)
    kapt(libs.room.kapt)

    //Compose
    implementation(libs.compose.navigation)
    implementation(libs.compose.viewmodel)

    //Coroutine
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    //Location
    implementation(libs.location)

    //hilt
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.android)
    implementation(libs.hilt.test)
    kapt(libs.hilt.kapt.compiler)
    kapt(libs.hilt.kapt.compiler.android)

    //Test
    testImplementation(libs.junit)
    testImplementation(libs.nhaarman.mockito)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.core)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.androidx.core.test)



    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}