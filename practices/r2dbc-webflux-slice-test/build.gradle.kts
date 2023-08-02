plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.security:spring-security-core")

    // reactor tool
    implementation("io.projectreactor:reactor-tools")

    // r2dbc
    implementation("io.asyncer:r2dbc-mysql:0.9.2")

    // rxjava
    implementation("io.reactivex.rxjava3:rxjava:_")

    if (System.getProperty("os.arch") == "aarch64" && System.getProperty("os.name") == "Mac OS X") {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:_:osx-aarch_64")
    }

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
}