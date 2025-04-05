plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    testImplementation(composeBom)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    implementation(libs.coil.compose)

    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)

    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.compose)

    debugImplementation(libs.androidx.uitooling)
    debugImplementation(libs.androidx.uitooling.preview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)
    testImplementation(libs.turbine)
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
