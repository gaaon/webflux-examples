pluginManagement {
    repositories {
        gradlePluginPortal()

        maven { setUrl("https://repo.spring.io/milestone") } // Spring milestones
        maven { setUrl("https://repo.spring.io/snapshot") } // Spring snapshots
    }

    plugins {
        id("de.fayard.refreshVersions") version "0.51.0"
    }
}

plugins {
    id("de.fayard.refreshVersions")
}

rootProject.name = "webflux-examples"

include("examples:async-programming")
include("examples:spring-webflux")
include("examples:kotlin-coroutine")
include("examples:kotlin-coroutine-usage")
include("examples:spring-test-reactive")
include("examples:reactive-microservice")

include("practices:completable-future")
include("practices:reactive-streams")
include("practices:nio-server")
include("practices:selector")
include("practices:reactor-pattern")
include("practices:reactor")
include("practices:netty")
include("practices:sse")
include("practices:webflux")
include("practices:webflux-image")
include("practices:websocket")
include("practices:mongo-chat")
include("practices:redis-noti")
include("practices:redis-webflux-image")
include("practices:r2dbc-webflux")
include("practices:r2dbc-webflux-unit-test")
include("practices:r2dbc-webflux-slice-test")
include("practices:r2dbc-webflux-integration-test")
include("practices:coroutine-mongo-chat")
include("practices:coroutine-r2dbc-webflux")
include("practices:proactor-pattern")

include("campusgram-practice:article:core")
include("campusgram-practice:article:persistence")
include("campusgram-practice:article:presentation")
include("campusgram-practice:image")
include("campusgram-practice:user")

include("campusgram:article:core")
include("campusgram:article:persistence")
include("campusgram:article:presentation")
include("campusgram:image")
include("campusgram:user")
include("campusgram:gateway")

include("study:study-spring-circuit-breaker")