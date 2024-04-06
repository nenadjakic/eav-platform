plugins {
    kotlin("jvm") version "1.9.23"
    id("java-library")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    runtimeOnly("org.postgresql:postgresql:42.7.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}