plugins {
    java
    idea
}

group = "com.pauldaniv.template.api"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(project(":model"))
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("io.github.openfeign:feign-okhttp:9.3.1")
    implementation("io.github.openfeign:feign-gson:9.3.1")
    implementation("io.github.openfeign:feign-slf4j:9.3.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
