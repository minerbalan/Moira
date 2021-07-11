val kotlinVersion = "1.5.10"
val junitJupiterVersion = "5.7.2"
val junitPlatformEngineVersion = "1.7.2"

plugins {
    // build.gradle.ktsは現段階でpluginsスコープ外の変数にアクセスできないのでplugins内部でも変数を再定義する
    val kotlinVersion = "1.5.10"
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm").version(kotlinVersion)
    kotlin("plugin.spring").version(kotlinVersion)
    kotlin("plugin.jpa").version(kotlinVersion)
    kotlin("kapt").version(kotlinVersion)
}

group = "com.minerbalan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.31.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.31.1")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.apache.httpcomponents:httpclient:4.5.11")
    implementation("com.rometools:rome:1.15.0")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("mysql:mysql-connector-java:8.0.18")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module= "mockito-core")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test:${kotlinVersion}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:${kotlinVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:${junitPlatformEngineVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${junitPlatformEngineVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-commons:${junitPlatformEngineVersion}")

    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.mockito:mockito-core:3.11.2")

    testImplementation("com.h2database:h2:1.4.200")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun"){
    jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5050")
}

tasks.named<Wrapper>("wrapper"){
    gradleVersion = "6.7.1"
}
