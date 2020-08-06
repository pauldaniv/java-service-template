plugins {
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "6.2.0"
    id("org.flywaydb.flyway") version "6.5.1"
}

group = "com.pauldaniv.template.service"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":api"))
    implementation(project(":persistence"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.codehaus.groovy:groovy")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-alpine")
        ports.set(listOf(9090, 8080))
        images.set(setOf("awesome-spring-boot:1.115", "awesome-spring-boot:latest"))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}
