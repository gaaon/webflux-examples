plugins {
    java
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
            vendor.set(JvmVendorSpec.ADOPTIUM)
        }
    }

    dependencies {
        // lombok
        annotationProcessor("org.projectlombok:lombok:_")
        compileOnly("org.projectlombok:lombok:_")

        // test
        testImplementation("org.junit.jupiter:junit-jupiter-api:_")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:_")
    }
}

val nonSpringProjects = listOf(
    "examples:async-programming",
    "examples:kotlin-coroutine",
    "practices:completable-future",
    "practices:reactive-streams",
    "practices:nio-server",
    "practices:selector",
    "practices:reactor-pattern",
).map { project(it) }

configure(nonSpringProjects) {
    dependencies {
        // sl4jf
        implementation("org.slf4j:slf4j-api:_")
        implementation("ch.qos.logback:logback-classic:_")
        implementation("ch.qos.logback:logback-core:_")
    }
}