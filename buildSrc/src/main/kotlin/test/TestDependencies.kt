package test

import deps.DependenciesVersions

object TestDependencies {
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${DependenciesVersions.JUNIT_VERSION}"
    const val ANDROIDX_ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${DependenciesVersions.ESPRESSO_CORE}"
    const val ANDROIDX_COMPOSE_UI_TEST =
        "androidx.compose.ui:ui-test-junit4:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_COMPOSE_UI_TEST_MANIFEST ="androidx.compose.ui:ui-test-manifest:${DependenciesVersions.COMPOSE_UI}"

    const val MOCKK = "io.mockk:mockk:${DependenciesVersions.MOCKK}"
    const val TURBINE = "app.cash.turbine:turbine:${DependenciesVersions.TURBINE}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${DependenciesVersions.COROUTINES_TEST}"

    // Macrobenchmark and UI Automator
    const val UI_AUTOMATOR = "androidx.test.uiautomator:uiautomator:${DependenciesVersions.UI_AUTOMATOR}"
    const val MACROBENCHMARK = "androidx.benchmark:benchmark-macro-junit4:${DependenciesVersions.MACROBENCHMARK}"
}