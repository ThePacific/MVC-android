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

-dontwarn org.mockito.**
-dontnote org.mockito.**

-dontwarn org.junit.**
-dontnote org.junit.**

-dontwarn junit.runner**
-dontnote junit.runner**
-dontwarn junit.framework.**
-dontnote junit.framework.**

-dontwarn android.test.**
-dontnote android.test.**

-dontwarn org.objenesis.instantiator.**
-dontnote org.objenesis.instantiator.**


-dontnote com.facebook.stetho.dumpapp.Dumper
-dontnote com.facebook.stetho.dumpapp.DumperContext
-dontnote com.facebook.stetho.okhttp3.StethoInterceptor
-dontnote com.facebook.stetho.okhttp3.StethoInterceptor$ForwardingResponseBody
-dontnote com.facebook.stetho.okhttp3.StethoInterceptor$OkHttpInspectorRequest
-dontnote com.facebook.stetho.okhttp3.StethoInterceptor$OkHttpInspectorResponse
-dontnote okhttp3.ResponseBody
-dontnote com.facebook.stetho.inspector.runtime.RhinoDetectingRuntimeReplFactory
-dontnote okhttp3.internal.platform.ConscryptPlatform