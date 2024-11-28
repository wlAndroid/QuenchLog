# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute


-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepclassmembers,allowobfuscation class * {
  @androidx.annotation.DoNotInline <methods>;
}
-keep,allowobfuscation @interface androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}

# retrofit
-keep class retrofit2.**{*;}
-keep class okio.**{*;}
-keep class okhttp3.**{*;}

# TradPlus
-keep class com.tradplus.ads.** { *; }
-keep class com.tradplus.crosspro.** { *; }
-keep class com.tp.** { *; }
-keep class com.iab.omid.library.tradplus.** { *; }

# Adjust
-keep class com.adjust.sdk.**{*;}

# String Fog
-keep class com.github.megatronking.stringfog.**{*;}

# android
-keep class androidx.**{*;}
-keep class kotlinx.**{*;}
-keep class kotlin.**{*;}
-keep class com.google.**{*;}