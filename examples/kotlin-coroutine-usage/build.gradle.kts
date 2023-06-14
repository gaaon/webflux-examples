import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:_")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:_")

    // reactor
    implementation("io.projectreactor:reactor-core:_")

    // rxjava3
    implementation("io.reactivex.rxjava3:rxjava:_")

    // mutiny
    implementation("io.smallrye.reactive:mutiny:_")
    implementation("io.smallrye.reactive:mutiny-kotlin:_")

    // spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // reactor tool
    implementation("io.projectreactor:reactor-tools")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "11"
    }
}