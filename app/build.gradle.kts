import build.BuildConfig
import build.BuildCreator
import build.BuildDimensions
import deps.androidx
import deps.coreDataModule
import deps.coreDesignSystemModule
import deps.coreDiModule
import deps.coreDomainModule
import deps.corePresentationModule
import deps.dataStore
import deps.featuresModule
import deps.koin
import deps.okHttp
import deps.retrofit
import deps.room
import deps.testDebugDeps
import deps.testDeps
import deps.testImplDeps
import flavors.BuildFlavor
import release.ReleaseConfig
import signing.BuildSigning
import signing.SigningTypes
import test.TestBuildConfig

plugins {
  id(plugs.BuildPlugins.ANDROID_APPLICATION)
  id(plugs.BuildPlugins.KOTLIN_ANDROID)
  id(plugs.BuildPlugins.ANDROID)
  id(plugs.BuildPlugins.KOTLIN_COMPOSE)
  id(plugs.BuildPlugins.UPDATE_DEPS_VERSIONS)
  id(plugs.BuildPlugins.KSP)
  id(plugs.BuildPlugins.BASELINE_PROFILE)
}

android {
  namespace = BuildConfig.APP_ID
  compileSdk = BuildConfig.COMPILE_SDK_VERSION

  defaultConfig {
    applicationId = BuildConfig.APP_ID
    minSdk = BuildConfig.MIN_SDK_VERSION
    targetSdk = BuildConfig.TARGET_SDK_VERSION
    versionCode = ReleaseConfig.VERSION_CODE
    versionName = ReleaseConfig.VERSION_NAME

    testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
  }

  signingConfigs {
    // BuildSigning.Release(project).create(this)
    // BuildSigning.ReleaseExternalQa(project).create(this)
    BuildSigning.Debug(project).create(this)
  }

  flavorDimensions.add(BuildDimensions.APP)

  buildTypes {
    BuildCreator.Release(project).create(this).apply {
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      // signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
    }
    BuildCreator.Debug(project).create(this).apply {
      signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
    }
    BuildCreator.ReleaseExternalQa(project).create(this).apply {
      // signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
    }
  }

  productFlavors {
    BuildFlavor.APP.create(this)
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  coreDiModule()
  corePresentationModule()
  coreDesignSystemModule()
  featuresModule()
  dataStore()
  androidx()
  room()
  okHttp()
  retrofit()
  koin()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
