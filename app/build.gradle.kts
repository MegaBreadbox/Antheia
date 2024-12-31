import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.antheia_plant_manager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.antheia_plant_manager"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.antheia_plant_manager.junit_runner.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val localFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(localFile.inputStream())
        val webClientId = properties.getProperty("WEB_CLIENT_ID")?: ""

        buildConfigField("String", "WEB_CLIENT_ID", webClientId)
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //extras
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.android.gms.play.services.auth)

    //daggerHilt
    ksp(libs.ksp.dagger.hilt)
    ksp(libs.ksp.dagger)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.google.dagger)
    implementation(libs.androidx.hilt)
    implementation(libs.androidx.hilt.hilt.work)

    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.ksp.room)


    //firebase
    implementation(libs.firebase.ui.auth)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashyltics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.authentication)
    implementation(libs.firebase.firestore)

    //credentials
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.auth)
    implementation(libs.googleid)

    //Serialization
    implementation(libs.kotlin.serialization)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    kspTest(libs.ksp.dagger.hilt)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    kspAndroidTest(libs.ksp.dagger.hilt)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}