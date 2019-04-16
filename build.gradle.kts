import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    application
}

group = "game.tank"
version = "1.0-SNAPSHOT"

repositories {
//    mavenCentral()
    maven ( "http://maven.aliyun.com/nexus/content/groups/public/")
    maven ( "https://jitpack.io")
}

application {
    mainClassName="StartKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

dependencies{
    compile ("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}