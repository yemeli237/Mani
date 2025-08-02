plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mani_group.mani"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mani_group.mani"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.database)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Add the dependency for the Google services Gradle plugin
    //id("com.google.gms.google-services") version "4.4.2" apply false


//    val nav_version = "2.8.4"


    val nav_version = "2.8.4"


    ////firebase
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics")

    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    /////firebase



    // Jetpack Compose Integration
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    // Views/Fragments Integration
    implementation ("androidx.navigation:navigation-fragment:$nav_version")
    implementation ("androidx.navigation:navigation-ui:$nav_version")

    // Feature module support for Fragments
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation ("androidx.navigation:navigation-testing:$nav_version")

    // JSON serialization library, works with the Kotlin serialization plugin.
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation ("androidx.room:room-runtime:2.3.0" )

    // system ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    implementation("androidx.activity:activity-compose:1.8.0-alpha07")
    //material-icons
//    implementation("androidx.compose.material:material-icons-extended:1.5.4")

    implementation ("androidx.compose.ui:ui:1.0.5" )
    implementation ("androidx.compose.material:material:1.0.5" )
    implementation ("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3" )
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("io.ktor:ktor-client-core:1.6.1")
    implementation ("io.ktor:ktor-client-android:1.6.1")

    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("org.java-websocket:Java-WebSocket:1.5.2")
    implementation ("com.google.code.gson:gson:2.8.8")


    ///
    implementation (libs.coil.kt.coil.compose)
    //noinspection UseTomlInstead
    implementation(libs.tbuonomo.dotsindicator)
    implementation(libs.colorpicker.compose)
//
//
//    ////firebase
//    // Import the Firebase BoM
//    implementation(libs.firebase.bom)
//
//    // When using the BoM, you don't specify versions in Firebase library dependencies
//
//    // Add the dependency for the Firebase SDK for Google Analytics
//    implementation(libs.firebase.analytics)
//
//    // TODO: Add the dependencies for any other Firebase products you want to use
//    // See https://firebase.google.com/docs/android/setup#available-libraries
//    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
//    implementation(libs.google.firebase.auth)
//    implementation(libs.google.firebase.firestore)
//    /////firebase
//
//
//
//    // Jetpack Compose Integration
//    implementation (libs.androidx.navigation.compose)
//
//    // Views/Fragments Integration
//    implementation (libs.androidx.navigation.fragment)
//    implementation (libs.androidx.navigation.ui)
//
//    // Feature module support for Fragments
//    implementation (libs.androidx.navigation.dynamic.features.fragment)
//
//    // Testing Navigation
//    androidTestImplementation (libs.androidx.navigation.testing)
//
//    // JSON serialization library, works with the Kotlin serialization plugin.
//    implementation (libs.kotlinx.serialization.json)
//    implementation (libs.androidx.room.runtime )
//
//    // system ui controller
//    implementation(libs.accompanist.systemuicontroller)
//    implementation(libs.androidx.activity.compose.v180alpha07)
//    //material-icons
////    implementation(libs.androidx.material.icons.extended)
//
//    implementation (libs.ui )
//    implementation ("androidx.compose.material:material:1.8.3" )
//    implementation (libs.ui.tooling.preview)
//    implementation (libs.androidx.activity.compose.v140)
//    implementation ("com.squareup.okhttp3:okhttp:4.10.0" )
//    implementation (libs.kotlinx.coroutines.android)
//    implementation ("io.ktor:ktor-client-core:1.6.1")
//    implementation (libs.ktor.client.android)
//
//    implementation ("com.google.code.gson:gson:2.10.1")
//    implementation (libs.java.websocket)
//    implementation ("com.google.code.gson:gson:2.10.1")
//
//
//    ///
//    implementation (libs.coil.kt.coil.compose)
//    //noinspection UseTomlInstead
//    implementation(libs.tbuonomo.dotsindicator)
//    implementation(libs.colorpicker.compose)

    //sdk de payement
    implementation (libs.androidsdk)

    //implementer plus d'icon
    implementation(libs.androidx.material.icons.extended.vversion)

//    implementation(libs.generativeai.z)
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ai)

    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-cio:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")



}