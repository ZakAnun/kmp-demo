plugins {
    id("com.android.application") version "8.11.1"
    id("org.jetbrains.kotlin.android") version "2.2.21"
}

android {
    namespace = "com.example.kmpdemo.host"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.kmpdemo.host"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // 只吃发布制品，不编译 shared 源码
    implementation("com.example.kmpdemo:shared:1.0.0")
}
