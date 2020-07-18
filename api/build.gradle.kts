import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    idea
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "com.pauldaniv.template.api"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("io.github.openfeign:feign-okhttp:9.3.1")
    implementation("io.github.openfeign:feign-gson:9.3.1")
    implementation("io.github.openfeign:feign-slf4j:9.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
