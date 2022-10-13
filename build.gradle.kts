plugins {
    kotlin("jvm") version "1.7.10"
    java
    application
}

group = "org.silica"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.4.2"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    maven {url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")}
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":lib"))
    implementation(project(":script-host"))
}

application {
    mainClass.set("MainKt")
}


