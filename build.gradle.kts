// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}
buildscript {
    dependencies {
        classpath( "com.github.megatronking.stringfog:gradle-plugin:5.2.0")
        // 选用加解密算法库，默认实现了xor算法，也可以使用自己的加解密库。
        classpath ("com.github.megatronking.stringfog:xor:5.0.0")
    }
}