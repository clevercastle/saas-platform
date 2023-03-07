subprojects {
    if ("common".equals(name)) {
        dependencies {
            implementation(project(":saas-core"))
            implementation(project(":saas-model"))
            implementation(project(":saas-util"))

            implementation("com.auth0:java-jwt:4.2.2")
            implementation("com.auth0:jwks-rsa:0.22.0")
            implementation("io.quarkus:quarkus-smallrye-jwt")
            implementation("io.quarkus:quarkus-security")
            compileOnly("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
        }
    }
    if (!"common".equals(name)) {
        dependencies {
            implementation(project(":app:common"))
            implementation(project(":saas-core"))
            implementation(project(":saas-model"))
            implementation(project(":saas-util"))
            // api security

            implementation("io.quarkus:quarkus-hibernate-validator")
            implementation("io.quarkus:quarkus-resteasy-jackson")
            implementation("io.quarkus:quarkus-resteasy")
            implementation("io.quarkus:quarkus-config-yaml")

            implementation("io.quarkus:quarkus-smallrye-openapi")
        }
    }
}