package koref.preprocessors.tokenizers

import koref.utils.SystemConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

internal class SimpleWhiteSpaceTest {
  companion object {
    private val resourcesDirectory = File("src/test/resources").absolutePath
    private val testSettings = "$resourcesDirectory/test-settings.yml"
    private val config = SystemConfig(testSettings)
  }

  @Test
  fun getAnnotations() {
    val sws = SimpleWhiteSpace("tokens", config)
    assertThat(sws.annotations.size).isEqualTo(0)
  }

  @Test
  fun writeAnnotationsToFile(@TempDir tempDir: Path) {
    val sws = SimpleWhiteSpace("tokens", config)
    sws.writeAnnotationsToFile(tempDir)
    val annotationFile = tempDir.resolve("tokens")
    assertThat(Files.exists(annotationFile)).isEqualTo(true)
  }

  @Test
  fun run() {
    val sws = SimpleWhiteSpace("tokens", config)
    assertDoesNotThrow { sws.run() }
  }

  @Test
  fun getAnnotationName() {
    val sws = SimpleWhiteSpace("tokens", config)
    assertThat(sws.annotationName).isEqualTo("tokens")
  }
}