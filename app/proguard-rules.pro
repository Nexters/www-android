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

# ====================== [project settings] ========================

# [디버깅 모드에서 난독화 제외 설정]
#-dontshrink
#-dontoptimize
#-dontobfuscate

# ==================================================================

# ========================== [Firebase] ============================

-keepattributes *Annotation*
-keepattributes Signature

# ==================================================================

# ====================== [class and google] ========================

-keep class com.google.android.** { *; }
-keep class google.** { *; }
-keep class android.** { *; }
-keep class androidx.** { *; }
-keep class com.android.** { *; }
-keep class com.google.** { *; }
-keep class lambda* { *; }
-keepclassmembers enum * {
    values(...);
    valueOf(...);
}
-keepclasseswithmembers class * {
    native <methods>;
}
-keep public class * extends android.app.Activity { *; }
-keep public class * extends android.app.Application { *; }
-keep public class * extends android.app.Service { *; }
-keep public class * extends android.content.BroadcastReceiver { *; }
-keep public class * extends android.content.ContentProvider { *; }
-keep public class * extends android.app.backup.BackupAgentHelper { *; }
-keep public class * extends android.preference.Preference { *; }
-keep public class * extends android.widget.TextView { *; }
-keep public class * extends android.widget.Button { *; }

# ==================================================================

# ==================== [ok http and retrofit] ======================

-keep class okhttp3.** { *; }
-keep class okio.** { *; }
-keep class retrofit2.** { *; }

# ==================================================================

# ========================== [glide image] =========================

-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *; }
-keep interface com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# ==================================================================