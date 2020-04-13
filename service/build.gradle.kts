plugins {
    java
    idea
    groovy
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("com.bmuschko.docker-spring-boot-application") version "6.2.0"
}

group = "com.pauldaniv.template"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":model"))
    implementation(project(":api"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.codehaus.groovy:groovy:3.0.0-alpha-4")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:8-alpine")
        ports.set(listOf(9090, 8080))
        images.set(setOf("awesome-spring-boot:1.115", "awesome-spring-boot:latest"))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}
