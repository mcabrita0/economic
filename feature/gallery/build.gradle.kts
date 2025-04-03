plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":core"))

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    testImplementation(composeBom)

    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
}

android {
    namespace = "com.miguel.economic.gallery"
    compileSdk = 35 // TODO: fix

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}
