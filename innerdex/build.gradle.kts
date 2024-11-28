import com.github.megatronking.stringfog.plugin.StringFogExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("stringfog")
}

configure<StringFogExtension> {
    implementation = "com.github.megatronking.stringfog.xor.StringFogImpl"
    kg = com.github.megatronking.stringfog.plugin.kg.RandomKeyGenerator()
    mode = com.github.megatronking.stringfog.plugin.StringFogMode.bytes// base64或者bytes
}


android {
    namespace = "com.example.innerdex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.innerdex"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.github.megatronking.stringfog:xor:5.0.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24") // 请根据实际的 Kotlin 版本修改

    // TradPlus
    implementation("com.tradplusad:tradplus:12.6.10.1")
    implementation("com.tradplusad:tp_exchange:40.12.6.10.1")
    implementation("com.tradplusad:tradplus-crosspromotion:27.12.6.10.1")

    //--------------------Adjust---------------------------------------
    implementation("com.adjust.sdk:adjust-android:4.38.5")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // FCM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")

}