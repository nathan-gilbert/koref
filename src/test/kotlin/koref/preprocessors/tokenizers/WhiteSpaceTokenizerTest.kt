package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.preprocessors.PreprocessorType
import koref.utils.KorefTests
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

internal class WhiteSpaceTokenizerTest : KorefTests() {
  companion object {
    private const val testSettings = """baseDataDir: /Users/nathan/Documents/Data/raw/example
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: example.train.filelist
testDataDir: .
testFileList: example.test.filelist
preprocessors:
  - tokenizer
"""
    private val ann = Annotation(AnnotationType.TOKEN, 0, 3, "test")
  }

  @Test
  fun `get the type`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    assertThat(sws.type).isEqualTo(PreprocessorType.TOKENIZER)
  }

  @Test
  fun `get the annotations`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    assertThat(sws.annotations.size).isEqualTo(0)
  }

  @Test
  fun `write annotations to file`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
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
  fun `run train`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    assertDoesNotThrow { sws.runTrain() }
  }

  @Test
  fun `run test`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    assertDoesNotThrow { sws.runTest() }
  }

  @Test
  fun `run tuning`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val newConfig = config
    newConfig.setTuneDataDir("0")
    newConfig.setTuneFileList("example.train.filelist")
    val sws = WhiteSpaceTokenizer("tokens", newConfig, arrayListOf(doc))
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `run no tokenizers`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val newConfig = config
    newConfig.getPreprocessors().remove("tokenizer")
    val sws = WhiteSpaceTokenizer("tokens", newConfig, arrayListOf(doc))
    assertDoesNotThrow { sws.runTrain() }
    assertDoesNotThrow { sws.runTest() }
    assertDoesNotThrow { sws.runTuning() }
  }

  @Test
  fun `get annotation name`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    assertThat(sws.annotationName).isEqualTo("tokens")
  }

  @Test
  fun `run tokenize`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val sws = WhiteSpaceTokenizer("tokens", config, arrayListOf(doc))
    sws.run(doc)
    assertThat(doc.annotations.size).isEqualTo(1)
    assertThat(doc.annotations[AnnotationType.TOKEN]?.size).isEqualTo(10)
  }
}
