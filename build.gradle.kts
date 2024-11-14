plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.jetbrains.dokka") version "1.9.20"
	id("project-report")
}
// Adjust jackson-version from 2.16 for dokka to work. See https://github.com/Kotlin/dokka/issues/3472
ext["jackson-bom.version"] = "2.15.3"

group = "com.example"
version = "0.0.1-SNAPSHOT"
val allureVersion = "2.25.0"
val aspectJVersion = "1.9.21"

// Define configuration for AspectJ agent
val agent: Configuration by configurations.creating {
	isCanBeConsumed = true
	isCanBeResolved = true
}
java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// Import allure-bom to ensure correct versions of all the dependencies are used
	testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
	// Add necessary Allure dependencies to dependencies section
	testImplementation("io.qameta.allure:allure-junit5")
	agent("org.aspectj:aspectjweaver:${aspectJVersion}")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
