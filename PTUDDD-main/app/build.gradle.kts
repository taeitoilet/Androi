import com.android.tools.r8.internal.de

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // hinh tron
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Action button
    implementation("com.google.android.material:material:1.11.0")

    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("com.applandeo:material-calendar-view:1.9.2")
    implementation ("com.opencsv:opencsv:4.6")
}
