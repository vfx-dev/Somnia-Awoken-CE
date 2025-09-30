plugins {
    java
    id("xyz.wagyourtail.unimined") version "1.4.2-SNAPSHOT"
}

val minecraft_version: String by project
val neoforge_version: String by project
val versionCurios: String by project

version = "0.1.0"

base {
    archivesName = "somnia-ce"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }

    withSourcesJar()
}

repositories {
    unimined.neoForgedMaven()
    maven {
        name = "Curios"
        url = uri("https://maven.theillusivec4.top")
    }
}

dependencies {
    compileOnly(group = "top.theillusivec4.curios", name = "curios-neoforge", version = versionCurios)
}

unimined.minecraft {
    version(minecraft_version)

    mappings {
        mojmap()
    }

    neoForge {
        loader(neoforge_version)
        accessTransformer("src/main/resources/META-INF/accesstransformer.cfg")
        mixinConfig("mixins.somnia.json")
    }

    defaultRemapJar = true
}

tasks.jar {
    enabled = false
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/mods.toml") {
        expand("version" to project.version)
    }
}