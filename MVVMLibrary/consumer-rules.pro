# AndroidX 防止混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keep @androidx.annotation.Keep class *
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**