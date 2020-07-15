package koref.preprocessors.tokenizers

import koref.data.AnnotationType
import koref.preprocessors.PreprocessorType
import koref.utils.KorefTests
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

internal class OpenNLPSentenceSplitterTest : KorefTests() {
  companion object {
    private const val testSettings = """baseDataDir: /Users/nathan/Documents/Data/raw/example
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: example.train.filelist
testDataDir: .
testFileList: example.test.filelist
preprocessors:
  - tokenizer
modelDir: /Users/nathan/Documents/Models/OpenNLP
"""
  }

  @Test
  fun `get the type`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val oss = OpenNLPSentenceSplitter("sentences", config, arrayListOf(doc))
    Assertions.assertThat(oss.type).isEqualTo(PreprocessorType.TOKENIZER)
  }

  @Test
  fun `run tokenize`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir, testSettings)
    val doc = getRawTextDoc(tempDir.toString())
    val oss = OpenNLPSentenceSplitter("sentences", config, arrayListOf(doc))
    oss.run(doc)
    Assertions.assertThat(doc.annotations.size).isEqualTo(1)
    Assertions.assertThat(doc.annotations[AnnotationType.SENTENCE]?.size).isEqualTo(2)
  }
}
