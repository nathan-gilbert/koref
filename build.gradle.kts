import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  idea
  id("application")
  kotlin("jvm") version "1.6.10"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
  id("jacoco")
  id("org.jetbrains.dokka") version "1.6.0"
}

java.sourceCompatibility = JavaVersion.VERSION_17

application {
  group = "koref"
  mainClass.set("koref.KorefDriverKt")
  version = "0.0.1-SNAPSHOT"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.1")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.apache.opennlp:opennlp-tools:1.9.3")
  implementation("edu.stanford.nlp:stanford-corenlp:4.3.2")
  testImplementation("org.assertj:assertj-core:3.21.0")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
  testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
  detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.19.0")
  detekt("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.6.10")
}

detekt {
  buildUponDefaultConfig = true // preconfigure defaults
  // point to your custom config defining rules to run, overwriting default behavior
  config = files("$projectDir/detekt.yml")
  autoCorrect = true
}

jacoco {
  toolVersion = "0.8.7"
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
  }
}

tasks.withType<Detekt>().configureEach {
  // Target version of the generated JVM bytecode. It is used for type resolution.
  jvmTarget = "17"
  reports {
    html.required.set(true) // observe findings in your browser with structure and code snippets
    xml.required.set(false) // checkstyle like format mainly for integrations like Jenkins
    txt.required.set(false) // similar to the console output, contains issue signature to manually edit baseline files
  }
}

tasks.jacocoTestReport {
  dependsOn(":test")
  reports {
    xml.required.set(false)
    csv.required.set(false)
    html.required.set(true)
    html.outputLocation.set(file("$buildDir/reports/jacocoHtml"))
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
