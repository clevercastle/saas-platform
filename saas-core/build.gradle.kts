configurations {
    all {
        exclude("org.eclipse.microprofile.config:microprofile-config-api")
    }
}
dependencies {
    api(project(":saas-base"))
    implementation(project(":saas-entity"))
    implementation(project(":saas-model"))

    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")

    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-security")
    implementation("com.auth0:auth0:2.1.0")
}