// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.8.1" apply false
    id("com.android.library") version "8.8.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
}
buildscript {
    extra.apply {
        set("nav_version", "2.8.4")
        set("room_version", "2.5.1")
    }
}