package koref.utils

import koref.data.RawTextDocument
import java.io.File
import java.nio.file.Path

open class KorefTests {
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

    fun makeSettings(baseDir: String, workingDir: String): String {
      val tempFileDir = File(baseDir + File.separator + "0")
      tempFileDir.mkdir()
      val tempFile = File(tempFileDir, "raw.txt")
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

  private fun getSettings(tempDir: Path): String {
    return makeSettings(tempDir.toString(), ".")
  }

  fun getConfig(tempDir: Path): SystemConfig {
    return SystemConfig(getSettings(tempDir))
  }

  fun getRawTextDoc(tempDir: String): RawTextDocument {
    return RawTextDocument("0", tempDir)
  }
}