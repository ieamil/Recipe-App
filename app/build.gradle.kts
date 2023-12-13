import Build_gradle.ExtraPropertiesExtension.Companion.room_version

class ExtraPropertiesExtension {
    companion object {
        var room_version: String = "2.4.0"
    }
}

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

ext {
    room_version = ("2.4.0")
}

android {
    compileSdk = 34
    namespace = "com.example.tasteteaser"

    defaultConfig {
        applicationId = "com.example.tasteteaser"
        minSdk = 24
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
    }
    
}

dependencies {
    implementation ("androidx.room:room-runtime:$rootProject.ext.room_version")
    implementation("androidx.room:room-common:2.6.0")
    annotationProcessor ("androidx.room:room-compiler:$rootProject.ext.room_version")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.android.gms:play-services-auth:19.2.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("com.google.firebase:firebase-auth:22.2.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.5.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //for retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("com.makeramen:roundedimageview:2.3.0")

    implementation ("pub.devrel:easypermissions:3.1.0")
    implementation ("com.google.android.material:material:1.2.1")
    //circle image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //scalable unit text size
    implementation ("com.intuit.ssp:ssp-android:1.0.6'")

    //scalable unit size
    implementation ("com.intuit.sdp:sdp-android:1.0.6")

    //room database
    implementation ("androidx.room:room-runtime:2.2.5")
    implementation ("androidx.room:room-ktx:2.2.1")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    //crop image library

    implementation ("com.theartofdev.edmodo:android-image-cropper:2.8.0")
    //coroutines core
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")



}
