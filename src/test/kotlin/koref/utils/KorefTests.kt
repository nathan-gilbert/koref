package koref.utils

import koref.data.RawTextDocument
import java.io.File
import java.nio.file.Path

open class KorefTests {
  companion object {
    private const val defaultSettingsText = """baseDataDir: <base-dir>
workingDir: <working-dir>
trainDataDir: .
trainFileList: <train-filelist>
testDataDir: .
testFileList: <test-filelist>
preprocessors:
  - tokenizer
modelDir: /Users/nathan/Documents/Models/OpenNLP
"""

    fun makeSettings(baseDir: String, workingDir: String, settingsText: String? = null): String {
      val tempFileDir = File(baseDir + File.separator + "0")
      tempFileDir.mkdir()
      val tempFile = File(tempFileDir, "raw.txt")
      tempFile.writeText("This is a test file. This is my second sentence.")
      val trainFile = File(baseDir, "train.filelist")
      trainFile.writeText("0")
      val testFile = File(baseDir, "test.filelist")
      testFile.writeText("0")

      val finalSettings = settingsText ?: defaultSettingsText
      return finalSettings
        .replace("<base-dir>", baseDir)
        .replace("<working-dir>", workingDir)
        .replace("<train-filelist>", trainFile.absolutePath)
        .replace("<testFileList>", testFile.absolutePath)
    }
  }

  private fun getSettings(tempDir: Path, testSettings: String? = null): String {
    return makeSettings(tempDir.toString(), ".", testSettings)
  }

  fun getConfig(tempDir: Path, testSettings: String? = null): SystemConfig {
    return SystemConfig(getSettings(tempDir, testSettings))
  }

  fun getRawTextDoc(tempDir: String): RawTextDocument {
    return RawTextDocument("0", tempDir)
  }

  fun getSettingsFile(tempDir: Path, settingsFileName: String, settingsText: String? = null): File {
    val tempFileDir = File(tempDir.toString() + File.separator + "0")
    tempFileDir.mkdir()

    val tempRawFile = File(tempFileDir, "raw.txt")
    tempRawFile.writeText("This is a test file.")
    val trainFile = File(tempDir.toAbsolutePath().toString(), "train.filelist")
    trainFile.writeText("0")
    val testFile = File(tempDir.toAbsolutePath().toString(), "test.filelist")
    testFile.writeText("0")

    val tempSettingsFile = File(tempDir.toString(), settingsFileName)
    val tempSettingsText = makeSettings(tempDir.toString(), ".", settingsText)
    tempSettingsFile.writeText(tempSettingsText)
    return tempSettingsFile
  }
}
