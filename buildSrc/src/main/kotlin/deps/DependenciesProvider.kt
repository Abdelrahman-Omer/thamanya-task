package deps

import ksp
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.coreDataModule() {
    moduleImplementation(project(":core:data"))
}

fun DependencyHandler.coreDiModule() {
    moduleImplementation(project(":core:di"))
}

fun DependencyHandler.coreUtilsModule() {
    moduleImplementation(project(":core:utils"))
}

fun DependencyHandler.coreDomainModule() {
    moduleImplementation(project(":core:domain"))
}

fun DependencyHandler.corePresentationModule() {
    moduleImplementation(project(":core:presentation"))
}

fun DependencyHandler.coreDesignSystemModule() {
    moduleImplementation(project(":core:designsystem"))
}

fun DependencyHandler.featuresModule() {
    moduleImplementation(project(":features:home"))
}

fun DependencyHandler.room() {
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    ksp(Dependencies.roomCompiler)
}

fun DependencyHandler.ktor() {
    implementation(Dependencies.ktorCore)
    implementation(Dependencies.ktorJson)
    implementation(Dependencies.ktorAndroid)
    implementation(Dependencies.ktorLogging)
    implementation(Dependencies.ktorOkhttp)
    implementation(Dependencies.ktorNegotiation)
}

fun DependencyHandler.dataStore() {
    implementation(Dependencies.kotlinSerilaizations)
    implementation(Dependencies.datastorePreferences)
    implementation(Dependencies.kotlinCollections)
    implementation(Dependencies.datastore)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverterGson)
    implementation(Dependencies.retrofitKotlinCoroutinesAdapter)
}

fun DependencyHandler.okHttp() {
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
}

fun DependencyHandler.koin() {
    implementation(Dependencies.koin)
}

fun DependencyHandler.coil() {
    implementation(Dependencies.coilCompose)
}

fun DependencyHandler.media() {
    implementation(Dependencies.media3ExoplayerDash)
    implementation(Dependencies.media3Exoplayer)
    implementation(Dependencies.media3Ui)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltCompose)
    implementation(Dependencies.hiltNavigation)
    ksp(Dependencies.hiltCompiler)
    ksp(Dependencies.hiltAgp)
    ksp(Dependencies.hiltCompilerKapt)

}

fun DependencyHandler.androidx() {
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(Dependencies.ANDROIDX_UI)
    implementation(Dependencies.ANDROIDX_UI_GRAPHICS)
    implementation(Dependencies.ANDROIDX_UI_TOOLING)
    implementation(Dependencies.ANDROIDX_MATERIAL3)
    implementation(Dependencies.WORK_RUNTIME)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.ANDROIDX_ACTIVITY)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_RUNTIME)
    implementation(Dependencies.navigation)
    implementation(Dependencies.navigation2)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.googleJson)
}

fun DependencyHandler.testDeps() {
    testImplementation(test.TestDependencies.ANDROIDX_JUNIT)
    testImplementation(test.TestDependencies.MOCKK)
    testImplementation(test.TestDependencies.TURBINE)
    testImplementation(test.TestDependencies.COROUTINES_TEST)
    testImplementation("junit:junit:4.13.2")
}

fun DependencyHandler.testImplDeps() {
    androidTestImplementation(test.TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(test.TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(test.TestDependencies.ANDROIDX_COMPOSE_UI_TEST)
}

fun DependencyHandler.testDebugDeps() {
    debugImplementation(Dependencies.ANDROIDX_UI_TOOLING_PREVIEW)
    debugImplementation(test.TestDependencies.ANDROIDX_COMPOSE_UI_TEST_MANIFEST)
}
