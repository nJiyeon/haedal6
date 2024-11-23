pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
        maven ( "https://devrepo.kakao.com/nexus/content/groups/public/" )
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
        maven ("https://devrepo.kakao.com/nexus/content/groups/public/" )
    }
}
rootProject.name = "Haedal6"
include(":app")
 