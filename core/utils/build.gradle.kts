import deps.coreDataModule
import deps.coreDomainModule
import deps.corePresentationModule
import deps.hilt
import deps.okHttp
import deps.testDeps
import deps.dataStore
import deps.featuresModule
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
    namespace = "com.thamanya.utils"
}

dependencies {
    coreDomainModule()
    coreDataModule()
    corePresentationModule()
    ktor()
    media()
    okHttp()
    dataStore()
    koin()
    koin()
    testDeps()
    testImplDeps()
    testDebugDeps()
}
