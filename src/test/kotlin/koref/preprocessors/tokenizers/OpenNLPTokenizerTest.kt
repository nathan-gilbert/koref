package koref.preprocessors.tokenizers

import koref.data.AnnotationType
import koref.data.RawTextDocument
import koref.preprocessors.PreprocessorType
import koref.utils.SystemConfig
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

internal class OpenNLPSentenceTokenizerTest {
  companion object {
    private const val testSettings = """baseDataDir: <base-dir>
workingDir: <working-dir>
trainDataDir: .
trainFileList: <train-filelist>
testDataDir: .
testFileList: <test-filelist>
preprocessors:
  - tokenizer
modelDir: /Users/nathan/Documents/Models/OpenNLP
"""
    private val config = SystemConfig(testSettings)
    private val doc = RawTextDocument("0", "/Users/nathan/Documents/Data/raw/example")

    fun makeSettings(baseDir: String, workingDir: String): String {
      val tempFile = File(baseDir + File.separator + "0", "test.txt")
      tempFile.writeText("This is a test file.")
      val trainFile = File(baseDir, "train.filelist")
      trainFile.writeText("0")
      val testFile =  File(baseDir, "test.filelist")
      testFile.writeText("0")

      return testSettings
          .replace("<base-dir>", baseDir)
          .replace("<working-dir>", workingDir)
          .replace("<train-filelist>", trainFile.absolutePath)
          .replace("<testFileList>", testFile.absolutePath)
    }
  }

  @Test
  fun getType() {
    val ot = OpenNLPTokenizer("tokens", config, arrayListOf(doc))
    Assertions.assertThat(ot.type).isEqualTo(PreprocessorType.TOKENIZER)
  }

  @Test
  fun run() {
    val ot = OpenNLPTokenizer("tokens", config, arrayListOf(doc))
    ot.run(doc)
    Assertions.assertThat(doc.annotations.size).isEqualTo(1)
    Assertions.assertThat(doc.annotations[AnnotationType.TOKEN]?.size).isEqualTo(321)
  }
}