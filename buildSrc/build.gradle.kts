plugins{
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Updated to Kotlin 2.1.0
    api(kotlin("gradle-plugin:2.1.0"))

    // Updated Android Gradle Plugin to 8.11.0
    implementation("com.android.tools.build:gradle:8.8.1")

    // Updated Kotlin Gradle Plugin to 2.1.0
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")

    // Updated Spotless - latest stable version
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")

    // Updated Detekt - latest stable version
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")

    // Updated Gradle Versions Plugin
    implementation("com.github.ben-manes:gradle-versions-plugin:0.52.0")

    // Updated Dokka - latest stable version
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")
    implementation("org.jetbrains.dokka:kotlin-as-java-plugin:2.0.0")

    // Updated JavaPoet
    implementation("com.squareup:javapoet:1.13.0")

    // Updated Kotlin Serialization plugin
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.1.0")

    // Updated Protobuf Gradle Plugin
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
}