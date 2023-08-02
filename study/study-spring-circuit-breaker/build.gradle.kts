plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")

    /* test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // lombok
    testAnnotationProcessor("org.projectlombok:lombok:_")
    testCompileOnly("org.projectlombok:lombok:_")

    // mockito
    testImplementation("org.mockito:mockito-core:_")

    // reactor test
    testImplementation("io.projectreactor:reactor-test:_")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.8")
    }
}