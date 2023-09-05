// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.com.google.relay.gradle.plugin)
        classpath (libs.google.services)
    }
}


@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("com.google.relay") version ("0.3.02")
    id("com.google.dagger.hilt.android") version ("2.44")  apply false
}
true // Needed to make the Suppress annotation work for the plugins block