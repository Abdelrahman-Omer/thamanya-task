import deps.androidx
import deps.coil
import deps.coreDataModule
import deps.coreDomainModule
import deps.corePresentationModule
import deps.coreDesignSystemModule
import deps.coreUtilsModule
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
    namespace = "com.thamanya.home"
}

dependencies {
    coreDataModule()
    coreUtilsModule()
    coreDomainModule()
    corePresentationModule()
    coreDesignSystemModule()
    ktor()
    androidx()
    okHttp()
    dataStore()
    koin()
    coil()
    testDeps()
    testImplDeps()
    testDebugDeps()
}