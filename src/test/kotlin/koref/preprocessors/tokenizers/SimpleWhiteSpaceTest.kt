package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

internal class SimpleWhiteSpaceTest {
  companion object {
    private val resourcesDirectory = File("src/test/resources").absolutePath
    private val testSettings = "$resourcesDirectory/test-settings.yml"
    private val config = SystemConfig(testSettings)
    private val ann = Annotation(AnnotationType.TOKEN, 0, 3, "test")
  }

  @Test
  fun `get the annotations`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf())
    assertThat(sws.annotations.size).isEqualTo(0)
  }

  @Test
  fun `write annotations to file`(@TempDir tempDir: Path) {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf())
    sws.addAnnotation(ann)
    assertThat(sws.annotations.size).isEqualTo(1)
    sws.writeAnnotationsToFile(tempDir.toString())
    val annotationFile = tempDir.resolve("tokens")
    assertThat(Files.exists(annotationFile)).isEqualTo(true)

    assertThrows<FileNotFoundException> {
      sws.writeAnnotationsToFile("/some/dir/that/doesnt/exist")
    }
  }

  @Test
  fun `run train`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf())
    assertDoesNotThrow { sws.runTrain() }
  }

  @Test
  fun `run test`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf())
    assertDoesNotThrow { sws.runTest() }
  }

  @Test
  fun `run tuning`() {
    val newConfig = config
    newConfig.setTuneDataDir("muc6-train")
    newConfig.setTuneFileList("muc6.train.filelist")
    val sws = SimpleWhiteSpace("tokens", newConfig, arrayListOf())
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `run no tokenizers`() {
    val newConfig = config
    newConfig.getPreprocessors().remove("tokenizer")
    val sws = SimpleWhiteSpace("tokens", newConfig, arrayListOf())
    assertDoesNotThrow { sws.runTrain() }
    assertDoesNotThrow { sws.runTest() }
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `get annotation name`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf())
    assertThat(sws.annotationName).isEqualTo("tokens")
  }
}