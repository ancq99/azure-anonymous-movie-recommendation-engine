import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.openapi.generator") version "6.0.1"

    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "pl.ncms"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val exposedVersion = "0.40.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:42.5.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
    dependsOn("generateKotlinServer")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("generateKotlinServer", GenerateTask::class) {
    generatorName.set("kotlin-spring")

    inputSpec.set("$rootDir/src/main/resources/api/frontend-api.yaml")
    outputDir.set("$rootDir/")
    apiPackage.set("pl.ncms.moviere.generated.api")
    invokerPackage.set("pl.ncms.moviere.generated")
    modelPackage.set("pl.ncms.moviere.generated.model")

    templateDir.set("$rootDir/src/main/resources/gen/")
    generateApiTests.set(false)
    modelNameSuffix.set("DTO")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8",
            "gradleBuildFile" to "false",
            "interfaceOnly" to "true",
            "BasePackage" to "com.easydiagrams.edwebapi.generated",
            "documentationProvider" to "springdoc",
            "useTags" to "true"
        )
    )
}

//tasks.register("generateTypescriptClient", GenerateTask::class) {
//    generatorName.set("typescript-angular")
//    modelNameSuffix.set("DTO")
//
//    inputSpec.set("$rootDir/src/main/resources/api/frontend-api.yaml")
//    outputDir.set("$rootDir/../movie-re-app/src/app/generated/")
//}

tasks.register("generateTypescriptClient", GenerateTask::class) {
    generatorName.set("typescript-fetch")
    modelNameSuffix.set("DTO")

    inputSpec.set("$rootDir/src/main/resources/api/frontend-api.yaml")
    outputDir.set("$rootDir/../movie-re-app/src/generated/")
}
