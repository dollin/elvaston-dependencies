plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.versions)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    id("jacoco")
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
    implementation(libs.spring.hateoas)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation(libs.jackson.module.kotlin)
    implementation(libs.caffeine)
    implementation(libs.spring.boot.starter.cache)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.kotlin.logging)
    implementation(libs.springdoc.openapi)
    implementation(libs.bcprov)
    implementation(libs.spring.boot.starter.liquibase)
    implementation("org.springframework.boot:spring-boot-h2console")
    runtimeOnly(libs.h2)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(libs.springmockk)
    testImplementation(libs.spring.boot.starter.webmvc.test)
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

tasks.bootRun {
    args("--spring.profiles.active=local")
}

detekt {
    config.setFrom("$rootDir/detekt.yml")
    buildUponDefaultConfig = true
}
