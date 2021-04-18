plugins {
    kotlin("jvm") version "1.4.32"

    id("com.github.johnrengelman.shadow") version "6.1.0"

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

    shadowJar {
        manifest {
            attributes["Main-Class"] = "net.hyren.discord.bot.DiscordBotApplication"
        }

        val fileName = "${project.name}.jar"

        archiveFileName.set("${project.name}.jar")

        doLast {
            try {
                val file = file("build/libs/$fileName")

                val toDelete = file("/home/cloud/output/$fileName")

                if (toDelete.exists()) toDelete.delete()

                file.copyTo(file("/home/cloud/output/$fileName"))
                file.delete()
            } catch (ex: java.io.FileNotFoundException) {
                ex.printStackTrace()
            }
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
    compileOnly(kotlin("stdlib"))

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
