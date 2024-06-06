plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile =
                file("C:\\myfolder\\study-independen\\aplikasi-android-pemula\\KeyStore\\jagaFaktaKeyStore.jks")
            storePassword = "jagafakta"
            keyAlias = "keyJagaFakta"
            keyPassword = "jagafakta"
        }
    }
    namespace = "com.jagaFakta.fact_check_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jagaFakta.fact_check_android"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.core:core-ktx:1.10.1")

    //bom firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    //firebase auth
    implementation("com.google.firebase:firebase-auth")
    //google play service
    implementation("com.google.android.gms:play-services-auth:20.7.0")
}