pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}
rootProject.name = "saas"

include("app:property")
include("app:common")
include("app:admin")
include("app:alpha")
include("app:portal")
include("saas-base")
include("saas-entity")
include("saas-core")