plugins {
    kotlin("jvm") version "1.9.25"
    id("io.qameta.allure") version "2.11.2"
    application

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.codeborne:selenide:7.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:6.3.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.12")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
    //allure
    testImplementation("io.qameta.allure:allure-junit5:2.27.0")
    testImplementation("io.qameta.allure:allure-commandline:2.27.0")
    testImplementation("io.qameta.allure:allure-assertj:2.27.0")
    testImplementation("io.qameta.allure:allure-rest-assured:2.27.0")
    testImplementation("io.qameta.allure:allure-java-commons:2.27.0")
    testImplementation("io.qameta.allure:allure-selenide:2.27.0")
    testImplementation("org.aspectj:aspectjweaver:1.9.19")
}
tasks.test {
    useJUnitPlatform()
    systemProperty("allure.results.directory", "${project.buildDir}/allure-results")

    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    }


    kotlin {
        jvmToolchain(17)
    }

    application {
        mainClass.set("MainKt")
    }
