subprojects {
    if ("common".equals(name)) {
        dependencies {
            implementation(project(":saas-base"))
            implementation(project(":saas-core"))

            implementation("com.auth0:java-jwt:4.2.2")
            implementation("com.auth0:jwks-rsa:0.22.0")
            implementation("io.quarkus:quarkus-smallrye-jwt")
            compileOnly("io.quarkus:quarkus-security")
            compileOnly("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
            compileOnly("io.quarkus:quarkus-resteasy")
        }
    }
    if (!"common".equals(name)) {
        dependencies {
            implementation(project(":app:common"))
            implementation(project(":saas-base"))
            implementation(project(":saas-core"))
            implementation(project(":saas-model"))

            // api security
            implementation("io.quarkus:quarkus-hibernate-validator")
            implementation("io.quarkus:quarkus-resteasy-jackson")
            implementation("io.quarkus:quarkus-resteasy")
            implementation("io.quarkus:quarkus-config-yaml")

            implementation("io.quarkus:quarkus-smallrye-openapi")
            compileOnly("org.hibernate:hibernate-core:6.2.1.Final")
        }
    }
}