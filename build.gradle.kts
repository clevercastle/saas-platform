plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.allopen") version "1.8.10"
    kotlin("kapt") version "1.8.10"
    id("io.quarkus") apply false
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val mapstructVersion = "1.5.3.Final"


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
    apply(plugin = "kotlin-allopen")
    apply(plugin = "org.jetbrains.kotlin.kapt")

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

    tasks.withType<JavaCompile> {
        options.compilerArgs = listOf("-Amapstruct.verbose=true")
    }

    buildDir = File("${rootProject.buildDir.path}/${project.name}")

    if (listOf("admin", "alpha", "portal").contains(name)) {
        sourceSets {
            main {
                resources.srcDirs("${rootProject.projectDir.path}/app/property/main")
            }
            main {
                resources.srcDirs("${rootProject.projectDir.path}/app/property/test")
            }
        }
    }

    configurations {
        all {
            exclude(group = "ch.qos.logback", module = "logback-core")
            exclude(group = "ch.qos.logback", module = "logback-classic")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("org.apache.commons:commons-lang3:3.12.0")

        implementation("org.mapstruct:mapstruct:1.5.5.Final")
        kapt("org.mapstruct:mapstruct-processor:1.5.5.Final")

        implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation("io.quarkus:quarkus-kotlin")
        implementation("io.quarkus:quarkus-jackson")
        implementation("io.quarkus:quarkus-arc")

        testImplementation("io.quarkus:quarkus-junit5")
        testImplementation("io.rest-assured:rest-assured")
    }

    allOpen {
        annotation("jakarta.ws.rs.Path")
        annotation("jakarta.persistence.context.ApplicationScoped")
        annotation("io.quarkus.test.junit.QuarkusTest")
    }
}
