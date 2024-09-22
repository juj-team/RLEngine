plugins {
    kotlin("jvm") version "1.9.22"
    id("xyz.jpenilla.run-paper") version "2.2.0"
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "ru.pp.july"
version = "0.0.1"
description = "july code, redefined again"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://mvn.lumine.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.minebench.de")
    }
    maven {
        url = uri("https://maven.playpro.com")
    }
    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }
}
dependencies {
    implementation(kotlin("reflect"))
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:33.0.0-jre")
    // https://mvnrepository.com/artifact/org.yaml/snakeyaml
    implementation("org.yaml:snakeyaml:2.2")
    // https://mvnrepository.com/artifact/io.netty/netty-all
    implementation("io.netty:netty-all:4.1.107.Final")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
    implementation(kotlin("stdlib"))
    // https://mvnrepository.com/artifact/LibsDisguises/LibsDisguises
    compileOnly("LibsDisguises:LibsDisguises:10.0.42")
    // https://github.com/Phoenix616/InventoryGui
    implementation("de.themoep:inventorygui:1.6.2-SNAPSHOT")
    // https://mvnrepository.com/artifact/com.bladecoder.ink/blade-ink
    implementation("com.bladecoder.ink:blade-ink:1.1.2")
    // https://maven.playpro.com/net/coreprotect/coreprotect/
    compileOnly("net.coreprotect:coreprotect:22.1")

    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8")

    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
}

tasks {
    test{
        useJUnitPlatform()
    }
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val props =
            mapOf(
                "version" to project.version,
                "description" to project.description,
                "apiVersion" to "1.19",
            )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
kotlin {
    jvmToolchain(17)
}