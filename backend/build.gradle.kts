import java.io.ByteArrayOutputStream

plugins {
	kotlin("jvm") version "2.1.20"
	kotlin("plugin.spring") version "2.1.20"
	kotlin("plugin.serialization") version "2.1.20"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.12.0"
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
val kotlinxCoroutinesVersion: String by project
val springBootVersion: String by project
val kotlinVersion: String by project

dependencies {
	implementation("ch.qos.logback:logback-classic:1.5.18")
	implementation("org.slf4j:slf4j-api:2.0.17")

	implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
	// exposed deps
	implementation("org.jetbrains.exposed:exposed-core:${exposedVersion}")
	implementation("org.jetbrains.exposed:exposed-json:${exposedVersion}")
	implementation("org.jetbrains.exposed:exposed-kotlin-datetime:${exposedVersion}")
	implementation("org.jetbrains.exposed:exposed-spring-boot-starter:${exposedVersion}")
	implementation("org.postgresql:postgresql:42.7.5")

	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")

	// test deps
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion") // Замените версию, если необходимо
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xcontext-parameters")
	}
	sourceSets {
		main {
			kotlin.srcDir("${buildDir}/generated/src/main/kotlin")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateKotlinApi") {
	apiPackage.set("ru.athletica.api")
	modelPackage.set("ru.athletica.api.schemas")
	inputSpec.set("${layout.projectDirectory}/../openapi/openapi.yaml")
	outputDir.set("${buildDir}/generated")
	generatorName.set("kotlin")
	configOptions.set(
		mapOf(
			"dateLibrary" to "kotlinx-datetime",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "kotlinx_serialization"
		)
	)
	globalProperties.set(
		mapOf(
			"models" to "",
			"modelDocs" to "false"
		)
	)
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateNextjsClient") {
	inputSpec.set("${layout.projectDirectory}/../openapi/openapi.yaml")
	outputDir.set("${layout.projectDirectory}/../frontend/api_client/")
	generatorName.set("typescript-axios")
	configOptions.set(
		mapOf(
			"supportsES6" to "true",
			"npmName" to "@athletica/client",
			"npmVersion" to "0.0.1"
		)
	)
}

tasks.named("compileKotlin") {
	dependsOn("generateKotlinApi")
	dependsOn("generateNextjsClient")
}

// Таска создания пустого файла миграций
tasks.register<Exec>("addChangelog") {
	group = "help"
	description = "Create an empty changelog file with next version."

	val changelogsDir: String by project // Чтение параметра из gradle.properties

	commandLine("git", "config", "--local", "user.email")

	val outputStream = ByteArrayOutputStream()
	standardOutput = outputStream

	doLast {
		val filename = project.property("changelog") as String? ?: throw IllegalArgumentException("changelog name must be provided")
		val directory = File(changelogsDir)
		val files = directory.listFiles()
		val currentNumber = files?.maxOfOrNull { file -> file.name.takeWhile { it != '-' }.toInt() }

		if (currentNumber != null) {
			val template = directory.resolve("../changelog.template").readText()
			val newVersion = (currentNumber + 1).toString().padStart(4, '0')
			val newFile = directory.resolve("${newVersion}-$filename.yaml")
			val user = outputStream.toString().trim()
			newFile.writeText(String.format(template, newVersion, user))
		} else {
			println("The directory does not exist or is not a directory.")
		}
	}
}
