subprojects {
    if (!"common".equals(name)) {
        dependencies {
            implementation("io.quarkus:quarkus-smallrye-openapi")
            implementation(project(":app:common"))
        }
    }
    dependencies {
        implementation(project(":saas-core"))
        implementation(project(":saas-model"))
    }
}


