pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pixify"
include(":app")
include(":core:domain")
include(":core:model")
include(":core:host")
include(":feature:home")
include(":feature:library")
include(":feature:settings")
include(":feature:search")
include(":core:ui")
include(":core:product")
include(":core:resources")
include(":feature:post")
include(":core:database")
include(":feature:login")
include(":core:firebase")
include(":core:datastore")
