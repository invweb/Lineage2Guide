# Lineage 2 Guide - ProGuard Rules

# Retrofit
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes AnnotationDefaults
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-keep class com.squareup.moshi.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Moshi
-keep class * extends com.squareup.moshi.JsonAdapter
-keepclassmembers class * {
    @com.squareup.moshi.@org.squareup.moshi.* <fields>;
}

# Coroutines
-keepclassmembers,allowobfuscation,allowshrinking class * {
    @kotlinx.coroutines.internal.** *;
}

# Hilt
-keep,allowobfuscation,allowshrinking @dagger.hilt.components.SingletonComponent class *

# Timber
-keep class timber.log.Timber { *; }

# General
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.internal.**
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
