import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
}

val bundleId = "com.ingridentify"

android {
    namespace = bundleId
    compileSdk = 34

    defaultConfig {
        applicationId = bundleId
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        buildConfigField(
            "String",
            "BASE_URL",
            '"' + (properties.getProperty("BASE_URL") ?: "https://ingridentify-api-xb5hnzr2ba-et.a.run.app/ingridentify/") + '"'
        )
        buildConfigField(
            "String",
            "ML_URL",
            '"' + (properties.getProperty("ML_URL") ?: "https://ml-api-xb5hnzr2ba-et.a.run.app/ingridentify/") + '"'
        )
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
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0-rc02")
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha07")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation("androidx.room:room-paging:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    ksp("androidx.room:room-compiler:2.6.0")
}