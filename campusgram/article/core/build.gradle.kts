dependencies {
    // javax.inject
    implementation("javax.inject:javax.inject:_")

    // nullable
    implementation("com.google.code.findbugs:jsr305:_")

    // reactor
    implementation("io.projectreactor:reactor-core:_")

    /* test */

    // mockito
    testImplementation("org.mockito:mockito-core:_")
    testImplementation("org.mockito:mockito-junit-jupiter:_")

    // reactor
    testImplementation("io.projectreactor:reactor-test:_")

    // lombok
    testAnnotationProcessor("org.projectlombok:lombok:_")
    testCompileOnly("org.projectlombok:lombok:_")
}