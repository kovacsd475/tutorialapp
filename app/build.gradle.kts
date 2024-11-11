plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.test.samplescannerapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.samplescannerapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions += listOf("environment")
    productFlavors {
        create("develop") {
            dimension = "environment"
            buildConfigField(type = "String", name = "WEBSOCKET_URL", "\"wss://...\"")
        }
        create("production") {
            dimension = "environment"
            buildConfigField(type = "String", name = "WEBSOCKET_URL", "\"wss://...\"")
        }
    }
}

dependencies {
//    implementation(":DataCollectionLibrary")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.crashlytics.ktx)
//    implementation(libs.firebase.analytics.ktx)

    // Moshi
    implementation(libs.moshi.kotlin)
//    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Okhttp3
    implementation(libs.okhttp3.okhttp)
    implementation(libs.logging.interceptor)

    // Event bus
    implementation(libs.otto)
//    Barcode reader SDK
    implementation(files("libs/DataCollection.aar"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}