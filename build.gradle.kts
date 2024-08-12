plugins {
    id("java")
    id("maven-publish")
}

group = "io.github.cichlidmc"
version = "1.0.1"

repositories {
    mavenCentral()
}

dependencies {
}

java.withSourcesJar()

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}
