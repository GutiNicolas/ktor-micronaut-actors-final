plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
    id("io.micronaut.application") version "4.2.0"
}

micronaut {
    version("4.2.0")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("app.*")
    }
}

application {
    mainClass.set("app.ApplicationKt")
}

repositories {
    mavenCentral()
}

allOpen {
    annotation("io.micronaut.http.annotation.Controller")
    annotation("jakarta.inject.Singleton")
}

dependencies {
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    runtimeOnly("io.micronaut:micronaut-router")
    kapt("io.micronaut:micronaut-inject-java")
}
