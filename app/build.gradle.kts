plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.foodfusion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodfusion"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled= true
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-runtime:2.7.6")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //gosn
    implementation ("com.google.code.gson:gson:2.10.1")
    //glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
   

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-database")

    //fragmentNavigation
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    //rounded image
    implementation ("com.makeramen:roundedimageview:2.3.0")
    //circle imageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //youtube player
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")
    //lottie file
    implementation ("com.airbnb.android:lottie:3.4.0")
    //room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    //Rx
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")
    implementation("androidx.room:room-rxjava3:$2.6.1")
    implementation ("androidx.palette:palette:1.0.0")
}