import java.net.URI

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = URI("https://repository.map.naver.com/archive/maven") }
    }
}

rootProject.name = "imdang"
include(":app")
include(":component")
include(":resource")
include(":remote")
include(":data")
include(":domain")
include(":local")
