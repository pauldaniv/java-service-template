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
    }

    dependencies {
        implementation("org.projectlombok:lombok:1.18.4")
        annotationProcessor("org.projectlombok:lombok:1.18.4")
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
}
