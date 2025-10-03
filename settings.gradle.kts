pluginManagement {
    repositories {
        gradlePluginPortal {
            content {
                excludeGroup("org.apache.logging.log4j")
            }
        }
        mavenCentral()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "SomniaCE"