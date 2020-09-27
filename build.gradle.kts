import com.google.protobuf.gradle.*

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    }
}

plugins {
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("com.google.protobuf") version "0.8.13"
    kotlin("plugin.spring") version "1.4.0"

    `maven-publish`
    java
    kotlin("jvm") version "1.4.10"
}

group = "net.lz1998"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.12.2")
    implementation("com.google.protobuf:protobuf-java-util:3.12.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

protobuf {
    generatedFilesBaseDir = "$projectDir/src"
    println(generatedFilesBaseDir)
    protoc { artifact = "com.google.protobuf:protoc:3.7.0" }
}
sourceSets {
    main {
        proto {
            // 除了默认的'src/main/proto'目录新增proto文件的方法
            srcDir("onebot_idl")
            include("**/*.proto")
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

