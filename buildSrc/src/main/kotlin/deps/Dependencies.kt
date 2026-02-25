package deps

object Dependencies {
    const val ANDROIDX_CORE = "androidx.core:core-ktx:${DependenciesVersions.CORE_KTX}"
    const val ANDROIDX_LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DependenciesVersions.LIFE_CYCLE_RUNTIME_KTX}"
    const val ANDROIDX_ACTIVITY_COMPOSE =
        "androidx.activity:activity-compose:${DependenciesVersions.ACTIVITY_COMPOSE}"
    const val ANDROIDX_UI = "androidx.compose.ui:ui:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_UI_GRAPHICS =
        "androidx.compose.ui:ui-graphics:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_UI_TOOLING_PREVIEW =
        "androidx.compose.ui:ui-tooling-preview:${DependenciesVersions.COMPOSE_UI}"

    const val ANDROIDX_UI_TOOLING =
        "androidx.compose.ui:ui-tooling:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_MATERIAL3 =
        "androidx.compose.material3:material3:${DependenciesVersions.MATERIAL_3}"
    const val WORK_RUNTIME = "androidx.work:work-runtime-ktx:${DependenciesVersions.RUN_TIME}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${DependenciesVersions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${DependenciesVersions.MATERIAL}"
    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-ktx:${DependenciesVersions.ANDROIDX_ACTIVITY}"

    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${DependenciesVersions.NAVIGATION}"
    const val navigation =
        "androidx.navigation:navigation-ui-ktx:${DependenciesVersions.NAVIGATION}"
    const val navigation2 = "androidx.navigation:navigation-compose:${DependenciesVersions.NAVIGATION}"
    const val googleJson = "com.google.code.gson:gson:${DependenciesVersions.GOOGLE_GSON}"

    const val COMPOSE_MATERIAL =
        "androidx.compose.material:material:${DependenciesVersions.COMPOSE_MATERIAL}"
    const val COMPOSE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-compose:${DependenciesVersions.COMPOSE_RUNTIME}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${DependenciesVersions.HILT}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${DependenciesVersions.HILT}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersions.HILT}"
    const val hiltCompose = "androidx.hilt:hilt-work:${DependenciesVersions.HILT_COMPOSE}"
    const val hiltCompilerKapt = "androidx.hilt:hilt-compiler:${DependenciesVersions.HILT_COMPOSE}"
    const val hiltNavigation =
        "androidx.hilt:hilt-navigation-compose:${DependenciesVersions.HILT_COMPOSE}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${DependenciesVersions.RETROFIT}"

    const val koin = "io.insert-koin:koin-androidx-compose:${DependenciesVersions.KOIN}"

    const val retrofitConverterGson =
        "com.squareup.retrofit2:converter-gson:${DependenciesVersions.RETROFIT}"
    const val retrofitKotlinCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependenciesVersions.RETROFIT_COROUTINE_ADAPTER_VERSION}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${DependenciesVersions.OKHTTP}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${DependenciesVersions.OKHTTP}"

    const val roomRuntime = "androidx.room:room-runtime:${DependenciesVersions.ROOM}"
    const val roomCompiler = "androidx.room:room-compiler:${DependenciesVersions.ROOM}"
    const val roomKtx = "androidx.room:room-ktx:${DependenciesVersions.ROOM}"

    const val datastore = "androidx.datastore:datastore:${DependenciesVersions.DATA_STORE}"
    const val datastorePreferences =
        "androidx.datastore:datastore-preferences:${DependenciesVersions.DATA_STORE}"
    const val kotlinCollections =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${DependenciesVersions.KOTLIN_COLLECTIONS}"
    const val kotlinSerilaizations =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${DependenciesVersions.KOTLIN_SERIALIZATIONS}"

    const val ktorCore = "io.ktor:ktor-client-core:${DependenciesVersions.KTOR}"
    const val ktorAndroid = "io.ktor:ktor-client-android:${DependenciesVersions.KTOR}"
    const val ktorNegotiation = "io.ktor:ktor-client-content-negotiation:${DependenciesVersions.KTOR}"
    const val ktorJson = "io.ktor:ktor-serialization-kotlinx-json:${DependenciesVersions.KTOR}"
    const val ktorLogging = "io.ktor:ktor-client-logging:${DependenciesVersions.KTOR}"
    const val ktorOkhttp = "io.ktor:ktor-client-okhttp:${DependenciesVersions.KTOR}"
    const val coilCompose = "io.coil-kt:coil-compose:${DependenciesVersions.COIL}"

    const val media3ExoplayerDash = "androidx.media3:media3-exoplayer-dash:${DependenciesVersions.MEDIA}"
    const val media3UiCompose = "androidx.media3:media3-ui-compose:${DependenciesVersions.MEDIA}"
    const val media3Exoplayer = "androidx.media3:media3-exoplayer:${DependenciesVersions.MEDIA}"
    const val media3Ui = "androidx.media3:media3-ui:${DependenciesVersions.MEDIA}"

}