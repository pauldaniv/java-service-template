plugins {
  id("com.bmuschko.docker-spring-boot-application") version "6.2.0"
}

version = "0.0.1-SNAPSHOT"

dependencies {
  implementation(project(":api"))
  implementation(project(":persistence"))
  implementation("com.pauldaniv.java.library.template:first:0.0.1-SNAPSHOT")
  implementation("com.pauldaniv.java.library.template:second:0.0.1-SNAPSHOT")
}

docker {
  springBootApplication {
    baseImage.set("openjdk:8-alpine")
    ports.set(listOf(9090, 8080))
    images.set(setOf("${getDockerRegistryUrl()}/${rootProject.name}/${rootProject.name}-service:${project.version}"))
    jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
  }
}

fun getDockerRegistryUrl(): String? {
  val targetRegistry = findParam("TARGET_REGISTRY") ?: "GITHUB"
  return findParam("${targetRegistry}_DOCKER_REGISTRY_URL") ?: findParam("DOCKER_REGISTRY_URL")
}

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)
