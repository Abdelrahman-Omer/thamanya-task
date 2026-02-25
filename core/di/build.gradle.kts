import deps.coreDataModule
import deps.coreDomainModule
import deps.coreUtilsModule
import deps.hilt
import deps.okHttp
import deps.testDeps
import deps.dataStore
import deps.featuresModule
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
    namespace = "com.thamanya.di"
}

dependencies {
    coreDomainModule()
    coreUtilsModule()
    coreDataModule()
    featuresModule()
    ktor()
    okHttp()
    dataStore()
    koin()
    koin()
    testDeps()
    testImplDeps()
    testDebugDeps()
}
