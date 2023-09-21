import com.example.interrapidismotest.buildsrc.Libs

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(project(":domain"))
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.JavaX.inject)
    implementation(Libs.Arrow.core)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvmToolchain(8)
}
