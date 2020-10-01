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
    id("com.github.johnrengelman.shadow") version "4.0.4"
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("com.google.protobuf") version "0.8.13"
    kotlin("plugin.spring") version "1.4.0"

    maven
    `maven-publish`
    java
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
}

group = "net.lz1998"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-javalite:3.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
}

protobuf {
    generatedFilesBaseDir = "$projectDir/src"
    println(generatedFilesBaseDir)
    protoc {
        // You still need protoc like in the non-Android case
        artifact = "com.google.protobuf:protoc:3.8.0"
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                remove("java")
                id("java") {
                    option("lite")
                }
            }
        }
    }
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
tasks.install {
    dependsOn("shadowJar")
}