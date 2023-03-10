configurations {
    all {
        exclude("org.eclipse.microprofile.config:microprofile-config-api")
    }
}
dependencies {
    implementation(project(":saas-base"))
    implementation(project(":saas-model"))

    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")

    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-security")
    implementation("com.auth0:auth0:1.44.2")
}