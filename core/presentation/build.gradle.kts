import deps.androidx
import deps.coil
import deps.coreDomainModule
import deps.hilt
import deps.okHttp
import deps.testDeps
import deps.dataStore
import deps.koin
import deps.ktor
import deps.media
import deps.testImplDeps
import deps.testDebugDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.thamanya.presentation"
}

dependencies {
    coreDomainModule()
    media()
    ktor()
    androidx()
    okHttp()
    dataStore()
    koin()
    coil()
    testDeps()
    testImplDeps()
    testDebugDeps()
    implementation(project(":core:designsystem"))
}
