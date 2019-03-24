plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.squareup.retrofit2:retrofit:2.5.1-SNAPSHOT")
    implementation("com.squareup.retrofit2:converter-gson:2.5.1-SNAPSHOT")
}
