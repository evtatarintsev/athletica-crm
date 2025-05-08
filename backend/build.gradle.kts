plugins {
	kotlin("jvm") version "2.1.20"
	kotlin("plugin.spring") version "2.1.20"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.athletica"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}
val exposedVersion: String by project
dependencies {
	implementation("ch.qos.logback:logback-classic:1.5.18")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
	// exposed deps
	implementation("org.jetbrains.exposed:exposed-core:${exposedVersion}")
	implementation("org.jetbrains.exposed:exposed-json:${exposedVersion}")
	implementation("org.jetbrains.exposed:exposed-spring-boot-starter:${exposedVersion}")
	implementation("org.postgresql:postgresql:42.7.5")
	// test deps
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
