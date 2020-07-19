import org.springframework.boot.gradle.tasks.bundling.BootJar
import com.rohanprabhu.gradle.plugins.kdjooq.*
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
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.6"
    id("nu.studer.jooq") version "4.2"
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
    implementation("org.postgresql:postgresql:42.2.12")
    implementation("org.codehaus.groovy:groovy:3.0.4")
    implementation ("org.jooq:jooq")
    jooqGeneratorRuntime("org.postgresql:postgresql:42.2.12")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

flyway {
    url = dbURL()
    user = dbUser()
    password = dbPass()
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:src/main/resources/migration/postgres")
}

jooqGenerator {
    configuration("primary", sourceSets.getByName("main")) {
        configuration = jooqCodegenConfiguration {
            jdbc {
                username = dbUser()
                password = dbPass()
                driver = "org.postgresql.Driver"
                url = dbURL()
            }

            generator {
                target {
                    packageName = "com.paul.template.db.jooq"
                    directory = "${project.buildDir}/generated/jooq/primary"
                }

                database {
                    name = "org.jooq.meta.postgres.PostgresDatabase"
                    inputSchema = "public"
                }
            }
        }
    }
}

tasks.register("makeMigration") {
    doLast {
        val migrationContext = project.findProperty("migrationName") ?: "migration"
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.hh.mm.ss"))
        val path = "${project.name}/src/main/resources/migration/postgres"
        val fileName = "${path}/V${timestamp}__${migrationContext}.sql"
        File(fileName).createNewFile()
    }
}

tasks.build {
    finalizedBy(tasks.flywayMigrate)
}

fun dbURL() = "jdbc:postgresql://${dbHost()}:${dbPort()}/${dbName()}"

fun dbHost() = findParam("DB_HOST") ?: "localhost"
fun dbPort() = findParam("DB_PORT") ?: 5432
fun dbUser() = findParam("DB_USER") ?: "test"
fun dbPass() = findParam("DB_PASS") ?: "test"
fun dbName() = findParam("DB_NAME") ?: "test"

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)
