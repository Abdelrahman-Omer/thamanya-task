import deps.hilt
import deps.okHttp
import deps.testDeps
import deps.dataStore
import deps.koin
import deps.ktor
import deps.testImplDeps
import deps.testDebugDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.thamanya.domain"
}

dependencies {
    ktor()
    okHttp()
    dataStore()
    koin()
    testDeps()
    testImplDeps()
    testDebugDeps()
}
