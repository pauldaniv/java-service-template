plugins {
    java
    idea
    kotlin("jvm")
}

group = "com.pauldaniv.template"
version = "1.0-SNAPSHOT"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
