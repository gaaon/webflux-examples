plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":campusgram:article:core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.cloud:" +
            "spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    // spring cloud stream
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    // r2dbc
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    /* test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // testcontainer
    testImplementation("org.testcontainers:testcontainers:_")
    testImplementation("org.testcontainers:junit-jupiter:_")
    testImplementation("org.testcontainers:mongodb:_")

    // reactor
    testImplementation("io.projectreactor:reactor-test:_")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.8")
    }
}