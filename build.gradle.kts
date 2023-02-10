plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.allopen") version "1.7.22"
    id("io.quarkus") apply false
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

allprojects {
    group = "org.clevercastle"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
    }

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.quarkus")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        kotlinOptions.javaParameters = true
    }


    if (listOf("app:property", "app:admin", "app:alpha", "app:portal", "portal", ":app:portal").contains(name)) {
        sourceSets {
            main {
                resources.srcDirs("${rootProject.projectDir.path}/app/property/main")
            }
            main {
                resources.srcDirs("${rootProject.projectDir.path}/app/property/test")
            }
        }
    }

    dependencies {
        // implementation("org.postgresql:postgresql:42.5.3")
        implementation("io.quarkus:quarkus-jdbc-postgresql")
        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-resteasy-jackson")
        implementation("io.quarkus:quarkus-hibernate-orm")
        implementation("io.quarkus:quarkus-rest-client")
        implementation("io.quarkus:quarkus-resteasy")
        implementation("io.quarkus:quarkus-config-yaml")
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-jackson")
        implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
        implementation("io.quarkus:quarkus-rest-client-jackson")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("io.quarkus:quarkus-arc")
        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
    }
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

