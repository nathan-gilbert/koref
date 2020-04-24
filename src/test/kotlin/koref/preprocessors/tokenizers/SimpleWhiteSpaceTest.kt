package koref.preprocessors.tokenizers

import koref.utils.SystemConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.assertj.core.api.Assertions.assertThat
import java.io.File

internal class SimpleWhiteSpaceTest {
  companion object {
    private val resourcesDirectory = File("src/test/resources").absolutePath
    private val testSettings = "$resourcesDirectory/test-settings.yml"
    private val config = SystemConfig(testSettings)
  }

  @Test
  fun getAnnotations() {
  }

  @Test
  fun writeAnnotationsToFile() {
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