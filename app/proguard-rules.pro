-keep class com.google.mlkit.** { *; }
-keep interface com.google.mlkit.** { *; }
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-keep class com.google.firebase.** { *; }
-keep interface com.google.firebase.** { *; }
-keep class com.rideassistant.models.** { *; }
-keep interface com.rideassistant.models.** { *; }
-keep class * implements android.os.Parcelable { *; }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
