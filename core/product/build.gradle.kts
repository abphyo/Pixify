@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.biho.product"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}

dependencies {

    implementation(project(":core:resources"))
    implementation(project(":core:ui"))

    // Mandatory Package - ui
    implementation(libs.bundles.androidx)
    implementation(platform(libs.compose.ui.bom))
    testImplementation(platform(libs.compose.ui.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.debug)
    testImplementation(libs.junit)

    // Koin Package
    implementation(libs.bundles.koin)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.animated.navigation.bar)

    // Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicator)

}