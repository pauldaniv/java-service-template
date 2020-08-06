plugins {
    base
    java
    idea
    kotlin("jvm") version "1.3.50" apply false
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "idea")

    repositories {
        jcenter()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/pauldaniv/bom-template")
            credentials {
                username = project.findProperty("gpr.usr") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

    dependencies {
        implementation(platform("com.paul:bom-template:0.0.+"))
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
    }

    idea {
        module {
            excludeDirs.addAll(listOf(
                    file(".idea"),
                    file(".gradle"),
                    file("gradle"),
                    file("build"),
                    file("out")
            ))
        }
    }
    configurations.all {
        resolutionStrategy.cacheDynamicVersionsFor(1, "minutes")
    }
}
