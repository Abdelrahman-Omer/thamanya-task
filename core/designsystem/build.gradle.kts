import deps.androidx
import plugs.SharedLibraryGradlePlugin

plugins {
    id("com.android.library")
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.thamanya.designsystem"
}

dependencies {
    androidx()
}
