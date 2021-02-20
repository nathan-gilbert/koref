import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  id("application")
  kotlin("jvm") version "1.4.30"
  id("io.gitlab.arturbosch.detekt") version "1.16.0-RC1"
  id("jacoco")
  id("org.jetbrains.dokka") version "1.4.20"
}

java.sourceCompatibility = JavaVersion.VERSION_11

application {
  group = "koref"
  mainClass.set("koref.KorefDriverKt")
  version = "0.0.1-SNAPSHOT"
}

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.+")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.apache.opennlp:opennlp-tools:1.9.2")
  implementation("edu.stanford.nlp:stanford-corenlp:4.0.0")
  testImplementation("org.assertj:assertj-core:3.12.2")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
  testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.2")
}

detekt {
  failFast = true // fail build on any finding
  buildUponDefaultConfig = true // preconfigure defaults
  // point to your custom config defining rules to run, overwriting default behavior
  config = files("$projectDir/detekt.yml")
  autoCorrect = true

  reports {
    html.enabled = true // observe findings in your browser with structure and code snippets
    xml.enabled = false // checkstyle like format mainly for integrations like Jenkins
    txt.enabled = false // similar to the console output, contains issue signature to manually edit baseline files
  }
}

jacoco {
  toolVersion = "0.8.6"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    this.jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
  }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "11"
}

tasks.jacocoTestReport {
  dependsOn(":test")
  reports {
    xml.isEnabled = false
    csv.isEnabled = false
    html.isEnabled = true
    html.destination = file("$buildDir/reports/jacocoHtml")
  }
}

tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      limit {
        minimum = "1.0".toBigDecimal()
      }
    }
  }
}
