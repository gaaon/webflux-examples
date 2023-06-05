import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3:_")

    // reactor
    implementation("io.projectreactor:reactor-core:_")

    // rxjava3
    implementation("io.reactivex.rxjava3:rxjava:_")

    // mutiny
    implementation("io.smallrye.reactive:mutiny:_")
    implementation("io.smallrye.reactive:mutiny-kotlin:_")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all", "-Xcontext-receivers")
        jvmTarget = "11"
    }
}