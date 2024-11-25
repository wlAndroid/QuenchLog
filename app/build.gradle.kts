import com.github.megatronking.stringfog.plugin.StringFogExtension

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
//    kotlin("kapt")
    id("stringfog")
    id("com.google.gms.google-services")
}
configure<StringFogExtension> {
    implementation = "com.github.megatronking.stringfog.xor.StringFogImpl"
    kg = com.github.megatronking.stringfog.plugin.kg.RandomKeyGenerator()
    mode = com.github.megatronking.stringfog.plugin.StringFogMode.bytes// base64或者bytes
}

android {
    namespace = "com.app.quench.log"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.log.usja"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.1"


        setProperty("archivesBaseName", "${rootProject.name}_${versionCode}(${versionName})")
//        signingConfig = signingConfigs.getAt("release")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        resValue("string", "app_name", "ABC")
//        val shareName = "share_authorities"
//        val shareValue = "${applicationId}.file_provider"
//        buildConfigField("String", shareName, "\"$shareValue\"")
////        resValue("string", actionMainKey, actionMain)
//        manifestPlaceholders[shareName] = shareValue
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
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

//    //--------------------Camera---------------------------------------
//    val camerax_version = "1.3.4"
//    implementation("androidx.camera:camera-core:${camerax_version}")
//    implementation("androidx.camera:camera-camera2:${camerax_version}")
////    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
//    implementation("androidx.camera:camera-view:${camerax_version}")
//    //--------------------QR---------------------------------------
//    implementation("com.google.zxing:core:3.5.3")
//    implementation("com.google.mlkit:barcode-scanning:17.3.0")
////    implementation("androidx.camera:camera-mlkit-vision:1.4.0-beta02")

    //--------------------Room---------------------------------------
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")


    //--------------------ext--------------------------------------
    implementation("androidx.activity:activity-ktx:1.9.3") //byViewModels
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    val lifecycle = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")//viewModelScope


    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //--------------------TradPlus---------------------------------------
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24") // 请根据实际的 Kotlin 版本修改

    // TradPlus
    implementation("com.tradplusad:tradplus:12.6.10.1")
    implementation("com.tradplusad:tp_exchange:40.12.6.10.1")
    implementation("com.tradplusad:tradplus-crosspromotion:27.12.6.10.1")

    // Pangle
    implementation("com.tradplusad:tradplus-pangle:19.12.6.10.1")
    implementation("com.pangle.global:ads-sdk:6.2.0.5")

    // Inmobi
    implementation("com.tradplusad:tradplus-inmobix:23.12.5.10.1")
    implementation("com.inmobi.monetization:inmobi-ads-kotlin:10.7.7")
    implementation("com.inmobi.omsdk:inmobi-omsdk:1.3.17.1")

    // Applovin
    implementation("com.applovin:applovin-sdk:12.6.1")
    implementation("com.tradplusad:tradplus-applovin:9.12.5.10.1")

    //--------------------Adjust---------------------------------------
    implementation("com.adjust.sdk:adjust-android:4.38.5")
    implementation("com.android.installreferrer:installreferrer:2.2")
    implementation("com.google.android.gms:play-services-ads-identifier:17.0.0")

    //facebook
//    implementation("com.facebook.android:facebook-android-sdk:17.0.0")

    implementation("com.tencent:mmkv:1.3.9")

    //vungle
    implementation("com.tradplusad:tradplus-vunglex:7.12.6.10.1")
    implementation("com.vungle:vungle-ads:7.4.1")

    // Bigo
    implementation("com.bigossp:bigo-ads:4.9.1")
    implementation("com.tradplusad:tradplus-bigo:57.12.6.10.1")

    // Mintegral
    implementation("com.tradplusad:tradplus-mintegralx_overseas:18.12.6.10.1")
    implementation("com.mbridge.msdk.oversea:newinterstitial:16.8.51")
    implementation("com.mbridge.msdk.oversea:mbbid:16.8.51")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.google.code.gson:gson:2.10.1")

    //workManager
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    // FCM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")
}