

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.adrammedia.easyfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adrammedia.easyfood"
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
        dataBinding =true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //navigation
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    //intuit
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.intuit.ssp:ssp-android:1.0.6")

    //gif
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")
    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation ("androidx.activity:activity-ktx:1.8.2")
    //Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-compiler:2.49")
    kapt ("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    //Room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")


    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    //OkHttp
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    //Coil
    implementation ("io.coil-kt:coil:2.5.0")

    implementation ("com.diogobernardino:williamchart:3.11.0")

    implementation ("org.jsoup:jsoup:1.17.1")
    //No internet
    implementation ("com.google.android.material:material:1.11.0")

    implementation ("org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0")
}
kapt {
    correctErrorTypes = true
}