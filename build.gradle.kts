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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("org.junit.platform:junit-platform-suite:1.8.1")
    testImplementation("io.cucumber:cucumber-java:6.11.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:6.11.0")
}

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["testImplementation"])
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

task("cucumberCli") {
    dependsOn("testClasses")
    doLast {
        javaexec {
            mainClass.set("io.cucumber.core.cli.Main")
            classpath = cucumberRuntime + sourceSets.main.get().output + sourceSets.test.get().output
            // Change glue for your project package where the step definitions are.
            // And where the feature files are.
            args = listOf("--plugin", "pretty", "--glue", "com.lohika", "src/test/resources")
        }
    }
}

tasks.register<Test>("cucumber") {
    useJUnitPlatform()
    System.setProperty("cucumber.junit-platform.naming-strategy", "long")
}

tasks.register<Test>("junit") {
    useJUnitPlatform {
        System.getProperty("includeTags")?.let {
            includeTags(it)
        }
    }
}