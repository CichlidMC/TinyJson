plugins {
    id("java")
    id("maven-publish")
}

group = "io.github.cichlidmc"
version = "1.2.0"

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
        maven("https://mvn.devos.one/snapshots") {
            name = "devOS"
            credentials(PasswordCredentials::class)
        }
    }
}
