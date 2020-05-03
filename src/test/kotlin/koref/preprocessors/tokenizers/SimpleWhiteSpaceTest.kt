package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.data.RawTextDocument
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
    private val doc = RawTextDocument("test", "My Test Document")
  }

  @Test
  fun `get the annotations`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    assertThat(sws.annotations.size).isEqualTo(0)
  }

  @Test
  fun `write annotations to file`(@TempDir tempDir: Path) {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    sws.addAnnotation(ann)
    assertThat(sws.annotations.size).isEqualTo(1)
    sws.writeAnnotationsToFile(tempDir.toString())
    val annotationFile = tempDir.resolve("annotations/tokens")
    assertThat(Files.exists(annotationFile)).isEqualTo(true)

    assertThrows<FileNotFoundException> {
      sws.writeAnnotationsToFile("/some/dir/that/doesnt/exist")
    }

    sws.writeAnnotationsToFile(tempDir.toString())
    assertThat(Files.exists(annotationFile)).isEqualTo(true)
  }

  @Test
  fun `run train`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    assertDoesNotThrow { sws.runTrain() }
  }

  @Test
  fun `run test`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    assertDoesNotThrow { sws.runTest() }
  }

  @Test
  fun `run tuning`() {
    val newConfig = config
    newConfig.setTuneDataDir("muc6-train")
    newConfig.setTuneFileList("muc6.train.filelist")
    val sws = SimpleWhiteSpace("tokens", newConfig, arrayListOf(doc))
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `run no tokenizers`() {
    val newConfig = config
    newConfig.getPreprocessors().remove("tokenizer")
    val sws = SimpleWhiteSpace("tokens", newConfig, arrayListOf(doc))
    assertDoesNotThrow { sws.runTrain() }
    assertDoesNotThrow { sws.runTest() }
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `get annotation name`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    assertThat(sws.annotationName).isEqualTo("tokens")
  }

  @Test
  fun `run tokenize`() {
    val sws = SimpleWhiteSpace("tokens", config, arrayListOf(doc))
    sws.tokenize(doc)
    assertThat(doc.annotations.size).isEqualTo(1)
    assertThat(doc.annotations[AnnotationType.TOKEN]?.size).isEqualTo(3)
  }
}