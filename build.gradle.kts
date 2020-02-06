import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val spek_version = "2.0.5"
val kotlin_version = "1.3.21"

plugins {
    java
    kotlin("jvm") version "1.3.21"
}

group = "in.ashishchaudhary.algorithms"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/spekframework/spek") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlin_version")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek_version") {
        exclude("org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek_version") {
        exclude("org.junit.platform")
        exclude("org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

sourceSets.getByName("main") {
    java.srcDir("src/main/java")
    java.srcDir("src/main/kotlin")
}
sourceSets.getByName("test") {
    java.srcDir("src/test/kotlin")
    resources.srcDir("src/test/resources")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
val clean: Delete by tasks
