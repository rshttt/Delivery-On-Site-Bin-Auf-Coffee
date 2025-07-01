// File: /settings.gradle.kts (Folder paling luar proyek Anda)

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// Terapkan plugin 'foojay-resolver' untuk menangani download JDK secara otomatis
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Delivery On Site Bin Auf Coffee"
include(":app")