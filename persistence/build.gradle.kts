import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    java
    idea
    groovy
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "6.2.0"
    id("org.flywaydb.flyway") version "6.5.1"
}

group = "com.pauldaniv.template.persistence"
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
    implementation("org.codehaus.groovy:groovy:3.0.4")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

flyway {
    url = dbURL()
    user = dbUser()
    password = dbPass()
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:src/main/resources/migration/postgres")
}

tasks.register("makeMigration") {
    doLast {
        val migrationContext = project.findProperty("migrationName") ?: "migration"
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.hh.mm.ss"))
        val path = "src/main/resources/migration/postgres"
        val fileName = "${path}/V${timestamp}__${migrationContext}.sql"
        File(fileName).createNewFile()
    }
}

fun dbURL() = "jdbc:postgresql://${dbHost()}:${dbPort()}/${dbName()}"

fun dbHost() = findParam("DB_HOST") ?: "localhost"
fun dbPort() = findParam("DB_PORT") ?: 54321
fun dbUser() = findParam("DB_USER") ?: "test"
fun dbPass() = findParam("DB_PASS") ?: "test"
fun dbName() = findParam("DB_NAME") ?: "test"

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)
