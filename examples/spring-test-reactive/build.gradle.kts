plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // reactor
    implementation("io.projectreactor:reactor-core:_")

    // r2dbc
    implementation("io.asyncer:r2dbc-mysql:0.9.2")

    /* test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // lombok
    testAnnotationProcessor("org.projectlombok:lombok:_")
    testCompileOnly("org.projectlombok:lombok:_")

    // mockito
    testImplementation("org.mockito:mockito-core:_")

    // reactor test
    testImplementation("io.projectreactor:reactor-test:_")

    // r2dbc h2
    testImplementation("io.r2dbc:r2dbc-h2")

    // h2
    testRuntimeOnly("com.h2database:h2:_")

    // embedded mongo
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")

    // testcontainer
    testImplementation("org.testcontainers:testcontainers:_")
    testImplementation("org.testcontainers:junit-jupiter:_")
    testImplementation("org.testcontainers:mongodb:_")
    testImplementation("org.testcontainers:mysql:_")
    testImplementation("org.testcontainers:r2dbc:_")

    // mysql
    testImplementation("mysql:mysql-connector-java:_")

    // mockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:_")
}

