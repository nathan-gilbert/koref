package koref.preprocessors.tokenizers

import koref.data.AnnotationType
import koref.data.RawTextDocument
import koref.preprocessors.PreprocessorType
import koref.utils.SystemConfig
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class OpenNLPSentenceSplitterTest {
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
    private val config = SystemConfig(testSettings)
    private val doc = RawTextDocument("0", "/Users/nathan/Documents/Data/raw/example")
  }

  @Test
  fun `get the type`() {
    val oss = OpenNLPSentenceSplitter("sentences", config, arrayListOf(doc))
    Assertions.assertThat(oss.type).isEqualTo(PreprocessorType.TOKENIZER)
  }

  @Test
  fun `run tokenize`() {
    val oss = OpenNLPSentenceSplitter("sentences", config, arrayListOf(doc))
    oss.run(doc)
    Assertions.assertThat(doc.annotations.size).isEqualTo(1)
    Assertions.assertThat(doc.annotations[AnnotationType.SENTENCE]?.size).isEqualTo(7)
  }
}
