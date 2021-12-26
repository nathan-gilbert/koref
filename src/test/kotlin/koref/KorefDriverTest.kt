package koref

import koref.utils.KorefTests
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class KorefDriverTest : KorefTests() {
  @Test
  fun `KorefDriver does not throw exception`() {
    assertDoesNotThrow { main(arrayOf()) }
  }

  @Test
  fun `KorefDriver gets settings file argument`() {
    assertDoesNotThrow { main(arrayOf("-s", "settings.yml")) }
  }

  @Test
  fun `KorefDrive handles bad settings and extra arguments`() {
    assertDoesNotThrow { main(arrayOf("-s", "", "-a", "arg1", "arg2")) }
  }

  @Test
  fun `KorefDriver handles malformed arguments`() {
    assertDoesNotThrow { main(arrayOf("s", "blah")) }
  }

  @Test
  fun `KorefDriver handles config being uninitialized`() {
    assertDoesNotThrow { main(arrayOf("-s", "this-doesnt-exist.yml")) }
  }

  @Test
  fun `KorefDriver handles no preprocessors`(@TempDir tempDir: Path) {
    val settingsText = """baseDataDir: $tempDir
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: train.filelist
testDataDir: .
testFileList: test.filelist"""

    val tempSettingsFile = getSettingsFile(tempDir, "test-settings-no-preprocessors.yml", settingsText)
    assertDoesNotThrow {
      main(
        arrayOf("-s", tempSettingsFile.absolutePath)
      )
    }
  }

  @Test
  fun `KorefDriver handles other preprocessors`(@TempDir tempDir: Path) {
    val settingsText = """baseDataDir: $tempDir
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: train.filelist
testDataDir: .
testFileList: test.filelist
preprocessors:
  - other
    """

    val tempSettingsFile = getSettingsFile(tempDir, "test-settings-other-processors.yml", settingsText)
    assertDoesNotThrow {
      main(
        arrayOf("-s", tempSettingsFile.absolutePath)
      )
    }
  }
}
