plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.serializationPlugin)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "kg.fuankan.tezcargo"
    compileSdk = 34

    defaultConfig {
        applicationId = "kg.fuankan.tezcargo"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val domain = "\"http://10.0.2.2:8080\""

        debug {
            buildConfigField("String", "BASE_URL", domain)
        }

        release {
            buildConfigField("String", "BASE_URL", domain)
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.chili)
    implementation(libs.hilt)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    kapt(libs.hiltCompiler)
    implementation(libs.gsonConverter)
    implementation(libs.gson)
    implementation(libs.okhttpInterceptor)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.navigationUi)
    implementation(libs.navigationFragment)
    implementation(libs.coroutines)
    implementation(libs.preference)
    implementation(libs.lifeCycleRuntime)
    implementation(libs.lifeCycleService)
    implementation(libs.kotlinSerialization)

    implementation("androidx.browser:browser:1.8.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}