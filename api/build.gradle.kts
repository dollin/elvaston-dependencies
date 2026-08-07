plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.versions)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    // BOM / platform
    implementation(platform(libs.otel.instrumentation.bom))

    // Spring Boot starters — core & web
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.jdbc)
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.restclient)
    implementation(libs.spring.boot.starter.aspectj)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.log4j2)
    implementation(libs.spring.boot.starter.cache)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.hateoas)
    implementation(libs.springdoc.openapi)

    // Security
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.oauth2.resource.server)

    // OpenTelemetry
    implementation(libs.otel.spring.boot.starter)

    // Database & persistence
    implementation(libs.oracle.jdbc)
    implementation(libs.spring.boot.starter.liquibase)

    // JSON / serialization
    implementation(libs.json)

    // Logging
    implementation(libs.kotlin.logging)

    // Utilities
    implementation(libs.caffeine)
    implementation(libs.bcprov)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    // Test
    testImplementation(libs.spring.boot.starter.test) {
        exclude(module = "mockito-core")
    }
    testImplementation(libs.spring.boot.starter.jdbc.test)
    testImplementation(libs.spring.boot.starter.liquibase.test)
    testImplementation(libs.spring.boot.starter.webmvc.test)
    testImplementation(libs.spring.security.test)
    testImplementation(libs.hamcrest)
    testImplementation(libs.h2)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.springmockk)
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
