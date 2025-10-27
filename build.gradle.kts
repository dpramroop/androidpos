// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    val roomversion = "2.8.2"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    kotlin("jvm") version "2.2.20"


    kotlin("plugin.serialization") version "2.2.20" apply false
    id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false
    id("androidx.room") version "2.8.2" apply false

}