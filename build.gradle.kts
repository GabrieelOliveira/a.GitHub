plugins {
    kotlin("jvm") version "1.4.32"

    application
    java
}

group = "net.hyren"
version = "0.1-ALPHA"

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

repositories {
    mavenCentral()

    mavenLocal()

    jcenter()

    maven("https://repo.gradle.org/gradle/libs-releases")
}

dependencies {
    // kotlin
    implementation(kotlin("stdlib"))

    // gradle
    implementation("org.gradle:gradle-tooling-api:7.0-rc-2")

    // redis
    compileOnly("redis.clients:jedis:3.3.0")

    // jackson
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.12.3")
    compileOnly("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

    // eventbus
    compileOnly("org.greenrobot:eventbus:3.2.0")

    // core-shared
    implementation("com.redefantasy:core-shared:0.1-ALPHA")
}
