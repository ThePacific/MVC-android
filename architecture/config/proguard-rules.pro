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
-keepclassmembers class android.support.v4.app.DialogFragment {
    boolean mDismissed;
    boolean mShownByMe;
}

-keepclasseswithmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

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
# add kotlin support
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-dontnote sun.misc.Unsafe
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

-keep public class * implements java.lang.annotation.Annotation


-dontnote com.bumptech.glide.GeneratedAppGlideModuleImpl
-dontnote kotlin.internal.jdk8.JDK8PlatformImplementations
-dontnote kotlin.internal.JRE8PlatformImplementations
-dontnote kotlin.internal.JRE7PlatformImplementations
-dontnote com.android.org.conscrypt.SSLParametersImpl
-dontnote org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
-dontnote dalvik.system.CloseGuard
-dontnote sun.security.ssl.SSLContextImpl
-dontnote android.databinding.DataBindingComponent
-dontnote android.arch.lifecycle.LifecycleOwner
-dontnote android.arch.lifecycle.Lifecycle$Event

-dontnote org.intellij.lang.annotations.Flow
-dontnote org.intellij.lang.annotations.Identifier
-dontnote org.intellij.lang.annotations.JdkConstants$AdjustableOrientation
-dontnote org.intellij.lang.annotations.JdkConstants$BoxLayoutAxis
-dontnote org.intellij.lang.annotations.JdkConstants$CalendarMonth
-dontnote org.intellij.lang.annotations.JdkConstants$CursorType
-dontnote org.intellij.lang.annotations.JdkConstants$FlowLayoutAlignment
-dontnote org.intellij.lang.annotations.JdkConstants$FontStyle
-dontnote org.intellij.lang.annotations.JdkConstants$HorizontalAlignment
-dontnote org.intellij.lang.annotations.JdkConstants$InputEventMask
-dontnote org.intellij.lang.annotations.JdkConstants$ListSelectionMode
-dontnote org.intellij.lang.annotations.JdkConstants$PatternFlags
-dontnote org.intellij.lang.annotations.JdkConstants$TabLayoutPolicy
-dontnote org.intellij.lang.annotations.JdkConstants$TabPlacement
-dontnote org.intellij.lang.annotations.JdkConstants$TitledBorderJustification
-dontnote org.intellij.lang.annotations.JdkConstants$TitledBorderTitlePosition
-dontnote org.intellij.lang.annotations.JdkConstants$TreeSelectionMode
-dontnote org.intellij.lang.annotations.JdkConstants
-dontnote org.intellij.lang.annotations.Language
-dontnote org.intellij.lang.annotations.MagicConstant
-dontnote org.intellij.lang.annotations.Pattern
-dontnote org.intellij.lang.annotations.PrintFormat
-dontnote org.intellij.lang.annotations.PrintFormatPattern
-dontnote org.intellij.lang.annotations.RegExp
-dontnote org.intellij.lang.annotations.Subst
-dontnote org.jetbrains.annotations.Contract
-dontnote org.jetbrains.annotations.Nls
-dontnote org.jetbrains.annotations.NonNls
-dontnote org.jetbrains.annotations.NotNull
-dontnote org.jetbrains.annotations.Nullable
-dontnote org.jetbrains.annotations.PropertyKey
-dontnote org.jetbrains.annotations.TestOnly
-dontnote android.net.http.SslError
-dontnote android.net.http.SslCertificate$DName
-dontnote android.net.http.SslCertificate
-dontnote android.net.http.HttpResponseCache
-dontnote org.apache.http.conn.scheme.SocketFactory
-dontnote org.apache.http.conn.scheme.HostNameResolver
-dontnote org.apache.http.conn.scheme.LayeredSocketFactory
-dontnote org.apache.http.conn.ConnectTimeoutException
-dontnote org.apache.http.params.CoreConnectionPNames
-dontnote org.apache.http.params.HttpParams
-dontnote org.apache.http.params.HttpConnectionParams