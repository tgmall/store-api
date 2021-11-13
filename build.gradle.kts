val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val mysql_version: String by project
val h2_version: String by project
val kotlin_coroutines_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
}

group = "wang.ralph.store"
version = "0.0.1"
application {
    mainClass.set("wang.ralph.store.ApplicationKt")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("at.favre.lib:bcrypt:0.9.0")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("wang.ralph.common:ktor-graphql:2.3.0-SNAPSHOT")
    implementation("com.expediagroup:graphql-kotlin-schema-generator:5.2.0")
    runtimeOnly("mysql:mysql-connector-java:$mysql_version")
    testImplementation("com.h2database:h2:$h2_version")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}

application {
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}
