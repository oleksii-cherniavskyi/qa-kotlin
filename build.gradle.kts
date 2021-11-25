plugins {
    kotlin("jvm") version "1.5.31"
    java
    id("io.qameta.allure") version "2.9.6"
    id("io.qameta.allure-report") version "2.9.6"
}

group = "org.lohika"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
}

allure {
    adapter {
        allureJavaVersion.set("2.16.1")
        aspectjVersion.set("1.9.6")

        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        aspectjWeaver.set(true)
    }
}

tasks.register<Test>("junit") {
    useJUnitPlatform {
        System.getProperty("includeTags")?.let {
            includeTags(it)
        }
    }
}
