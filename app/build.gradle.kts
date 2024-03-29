subprojects {
    if ("common".equals(name)) {
        dependencies {
            implementation(project(":saas-base"))
            implementation(project(":saas-core"))
            implementation(project(":saas-model"))

            implementation("com.auth0:java-jwt:4.4.0")
            implementation("com.auth0:jwks-rsa:0.22.0")
            implementation("io.quarkus:quarkus-smallrye-jwt")
            compileOnly("io.quarkus:quarkus-security")
            compileOnly("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
            compileOnly("io.quarkus:quarkus-resteasy")
        }
    }
    if (!"common".equals(name)) {
        dependencies {
//            implementation("io.quarkus:quarkus-amazon-lambda-http")
            implementation(project(":app:common"))
            implementation(project(":saas-base"))
            implementation(project(":saas-core"))
            implementation(project(":saas-entity"))
            implementation(project(":saas-model"))
            implementation("io.quarkus:quarkus-logging-json")
            implementation("jakarta.persistence:jakarta.persistence-api")
            compileOnly("org.hibernate.orm:hibernate-core")

            implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

            // api security
            implementation("io.quarkus:quarkus-hibernate-validator")
            implementation("io.quarkus:quarkus-resteasy-jackson")
            implementation("io.quarkus:quarkus-resteasy")
            implementation("io.quarkus:quarkus-config-yaml")

            implementation("io.quarkus:quarkus-smallrye-openapi")
        }
    }
}