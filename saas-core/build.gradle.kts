dependencies {
    implementation(project(":saas-model"))
    implementation(project(":saas-util"))

    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")

    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("com.auth0:auth0:1.44.2")
}
