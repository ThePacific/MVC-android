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
#-renamesourcefileattribute SourceFile
-keep class android.databinding.DataBindingUtil { *; }
-keep public class android.support.v4.content.FileProvider { *; }
-keep class com.boo.partner.view.wheel.** { *; }
-keep class net.cachapa.expandablelayout.** { *; }
-keepclassmembers class android.support.v4.app.DialogFragment {
   boolean mDismissed;
   boolean mShownByMe;
}

-keepclasseswithmembers class * {
   @android.webkit.JavascriptInterface <methods>;
}

-keep class java.lang.Object
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
-keepnames class * implements android.os.Parcelable {
  public static final ** CREATOR;
}

-dontwarn com.google.errorprone.annotations.**
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.inject.**
-dontwarn javax.annotation.**
-dontwarn com.uber.javaxextras.**


##---------------Begin: proguard configuration for Okhttp3 and Retrofit  ----------
-dontwarn java.nio.file.*
-dontwarn okio.**
-dontwarn okhttp3.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
##---------------End: proguard configuration for Okhttp3 and Retrofit  ----------


##---------------Begin: proguard configuration for Moshi/Gson----------
# Gson specific classes
#-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
-keepclasseswithmembers class * {
  @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
##---------------End: proguard configuration for Moshi/Gson ----------


##---------------Begin: proguard configuration for glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
# -keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for glide  ----------


# Required to preserve the Flurry SDK
-dontnote com.flurry.sdk.**
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**
-keepattributes *Annotation*,EnclosingMethod,Signature
-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class * implements android.arch.lifecycle.GeneratedAdapter {<init>(...);}