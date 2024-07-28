plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2" // یا نسخه مناسب با نسخه کامپایلر شما
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.expensemaster"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expensemaster"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    // Room components
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.activity)
    ksp("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.google.android.material:material:1.5.0")
}

ksp {
    arg("room.incremental", "true")
}
kapt {
    correctErrorTypes = true
    useBuildCache = true
}

