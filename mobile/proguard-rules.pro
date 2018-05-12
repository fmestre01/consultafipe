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
-keep class com.google.** {*;}
-dontwarn org.apache.http.params.*
-dontwarn com.fasterxml.jackson.databind.ext**
-dontwarn org.junit.internal**
-dontwarn org.junit.rules.**
-dontwarn android.test.**
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote junit.**
-dontnote com.google.**
-dontnote okhttp3.internal.**
-dontnote udacity.com.core.util.numberpicker.**
-keepnames class com.fasterxml.jackson.** {
*;
}
-keepnames interface com.fasterxml.jackson.** {
    *;
}
-keep class android.support.v7.widget.SearchView { *; }
-keep public class * extends util.DeviceUtils