subprojects {
    if (!"common".equals(name)) {
        dependencies {
            implementation(project(":app:common"))
            implementation(project(":saas-core"))
            implementation(project(":saas-model"))
            implementation(project(":saas-util"))
            implementation("io.quarkus:quarkus-smallrye-openapi")
            implementation("io.quarkus:quarkus-hibernate-validator")
            implementation("io.quarkus:quarkus-resteasy-jackson")
            implementation("io.quarkus:quarkus-resteasy")
            implementation("io.quarkus:quarkus-config-yaml")
        }
    }
    dependencies {
        implementation(project(":saas-core"))
        implementation(project(":saas-model"))
        implementation(project(":saas-util"))
    }

}