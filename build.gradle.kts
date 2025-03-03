buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}

// Tüm modüller için Kotlin versiyonunu ayarlıyoruz
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
            // Compose için Kotlin uyumluluk kontrolünü etkinleştiriyoruz
            freeCompilerArgs = listOf(
                "-Xjsr305=strict",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
}
