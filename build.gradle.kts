import net.minecraftforge.gradle.common.util.RunConfig

plugins {
    java
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
}

val minecraft_version: String by project
val forge_version: String by project
val versionCurios: String by project

version = "3.5.0"

base {
    archivesName = "somnia-ce-forge1.20.1"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }

    withSourcesJar()
}

repositories {
    mavenCentral()
    maven {
        name = "Curios"
        url = uri("https://maven.theillusivec4.top")
    }
}

dependencies {
    compileOnly("top.theillusivec4.curios:curios-forge:$versionCurios")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0")
    compileOnly("io.github.llamalad7:mixinextras-common:0.5.0")
    implementation(jarJar("io.github.llamalad7:mixinextras-forge:0.5.0") {
        jarJar.ranged(this, "[0.5.0,)")
    })
    minecraft("net.minecraftforge:forge:${minecraft_version}-${forge_version}")
}

minecraft {
    mappings("official", "1.20.1")
    copyIdeResources = true
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))
    runs {
        val config = Action<RunConfig> {
            properties(mapOf(
                "forge.logging.console.level" to "debug"
            ))
            workingDirectory = project.file("run").canonicalPath
            source(sourceSets.main.get())
        }

        create("client", config)
        create("server", config)
    }
}

tasks.jar {
    finalizedBy("reobfJar")
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/mods.toml") {
        expand("version" to project.version)
    }
}