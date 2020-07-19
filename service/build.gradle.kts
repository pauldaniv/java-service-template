plugins {
    java
    idea
    groovy
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "6.2.0"
    id("org.flywaydb.flyway") version "6.5.1"
}

group = "com.pauldaniv.template.service"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":api"))
    implementation(project(":persistence"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.codehaus.groovy:groovy:3.0.4")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-alpine")
        ports.set(listOf(9090, 8080))
        images.set(setOf("awesome-spring-boot:1.115", "awesome-spring-boot:latest"))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}

fun dbURL() = "jdbc:postgresql://${dbHost()}:${dbPort()}/${dbName()}"

fun dbHost() = findParam("DB_HOST") ?: "localhost"
fun dbPort() = findParam("DB_PORT") ?: 54321
fun dbUser() = findParam("DB_USER") ?: "test"
fun dbPass() = findParam("DB_PASS") ?: "test"
fun dbName() = findParam("DB_NAME") ?: "test"

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)
