plugins {
    kotlin("jvm") version "1.9.25"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //testImplementation(kotlin("test"))
    implementation("com.codeborne:selenide:7.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.0")
    implementation("io.github.bonigarcia:webdrivermanager:6.3.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.12")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}