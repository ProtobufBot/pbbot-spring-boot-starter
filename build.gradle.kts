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
    signing
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
}

group = "net.lz1998"
version = "0.0.15"

repositories {
    mavenCentral()
}

dependencies {
    api("com.google.protobuf:protobuf-javalite:3.8.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    api("org.springframework.boot:spring-boot-starter-websocket")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    api(kotlin("stdlib-jdk8"))
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
java {
    withJavadocJar()
    withSourcesJar()
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
    jar {
        enabled = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("pbbot-spring-boot-starter")
                description.set("A spring boot starter for qq bot development.")
                url.set("https://cq.lz1998.net")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("lz1998")
                        name.set("lizheng")
                        email.set("875543533@qq.com")
                    }
                    developer {
                        id.set("life")
                        name.set("wangguogang")
                        email.set("13122192336@163.com")
                    }
                }
                scm {
                    url.set("scm:git:git@github.com:lz1998/spring-cq.git")
                    developerConnection.set("scm:git:git@github.com:lz1998/spring-cq.git")
                    connection.set("scm:git:git@github.com:lz1998/spring-cq.git")
                }

            }
        }
    }

    repositories {
        maven {
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            credentials {
                username = properties["sonatype_username"].toString()
                password = properties["sonatype_password"].toString()
            }
        }
    }
}
signing {
    useGpgCmd()
    sign(publishing.publications["maven"])
}