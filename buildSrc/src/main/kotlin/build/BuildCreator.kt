package build

import extensions.buildConfigBooleanField
import extensions.buildConfigIntField
import extensions.buildConfigStringField
import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.LibraryBuildType
import extensions.getLocalProperty
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

sealed class BuildCreator(val name: String) {

    abstract fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType

    abstract fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType


    class Debug(private val project: Project) : BuildCreator(BuildTypes.DEBUG) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Sandbox.isMinifyEnabled
                isDebuggable = Build.Sandbox.isDebuggable
                versionNameSuffix = Build.Sandbox.versionNameSuffix
                applicationIdSuffix = Build.Sandbox.applicationIdSuffix
                enableUnitTestCoverage = Build.Sandbox.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Sandbox.isMinifyEnabled
                enableUnitTestCoverage = Build.Sandbox.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }
    }

    class Release(private val project: Project) : BuildCreator(BuildTypes.RELEASE) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage
                isDebuggable = Build.Release.isDebuggable

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("release.map_key")
                )

            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.getByName(name) {
                isMinifyEnabled = Build.Release.isMinifyEnabled
                enableUnitTestCoverage = Build.Release.enableUnitTestCoverage


                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("release.map_key")
                )

            }
        }
    }

    class ReleaseExternalQa(private val project: Project) :
        BuildCreator(BuildTypes.RELEASE_EXTERNAL_QA) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>): ApplicationBuildType {
            return namedDomainObjectContainer.create(name) {
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage
                isDebuggable = Build.ReleaseExternalQa.isDebuggable
                versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
                applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryBuildType>): LibraryBuildType {
            return namedDomainObjectContainer.create(name) {
                isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
                enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_BASE_URL,
                    project.getLocalProperty("dev.home_section_endpoint")
                )

                buildConfigStringField(
                    BuildVariables.HOME_SECTION_SEARCH_BASE_URL,
                    project.getLocalProperty("dev.search_home_section_endpoint")
                )

                buildConfigIntField(
                    BuildVariables.DB_VERSION,
                    project.getLocalProperty("dev.db_version")
                )
                buildConfigBooleanField(
                    BuildVariables.CAN_CLEAR_CACHE,
                    project.getLocalProperty("dev.clear_cache")
                )
                buildConfigStringField(
                    BuildVariables.MAP_KEY,
                    project.getLocalProperty("dev.map_key")
                )
            }
        }
    }
}