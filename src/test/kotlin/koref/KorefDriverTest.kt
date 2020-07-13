package koref

import koref.utils.KorefTests
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File
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
    val tempFile = File(tempDir.toString(), "test-settings-no-preprocessors.yml")
    tempFile.writeText("""baseDataDir: $tempDir
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: train.filelist
testDataDir: .
testFileList: test.filelist""")
    val tempFileDir = File(tempDir.toString() + File.separator + "0")
    tempFileDir.mkdir()
    val tempRawFile = File(tempFileDir, "raw.txt")
    tempRawFile.writeText("This is a test file.")
    val trainFile = File(tempDir.toAbsolutePath().toString(), "train.filelist")
    trainFile.writeText("0")
    val testFile =  File(tempDir.toAbsolutePath().toString(), "test.filelist")
    testFile.writeText("0")

    assertDoesNotThrow { main(
        arrayOf("-s", tempFile.absolutePath)
    ) }
  }

  @Test
  fun `KorefDriver handles other preprocessors`(@TempDir tempDir: Path) {
    val tempFile = File(tempDir.toString(), "test-settings-other-preprocessors.yml")
    tempFile.writeText("""baseDataDir: $tempDir
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: train.filelist
testDataDir: .
testFileList: test.filelist
preprocessors:
  - other
    """)
    val tempFileDir = File(tempDir.toString() + File.separator + "0")
    tempFileDir.mkdir()
    val tempRawFile = File(tempFileDir, "raw.txt")
    tempRawFile.writeText("This is a test file.")
    val trainFile = File(tempDir.toAbsolutePath().toString(), "train.filelist")
    trainFile.writeText("0")
    val testFile =  File(tempDir.toAbsolutePath().toString(), "test.filelist")
    testFile.writeText("0")
    assertDoesNotThrow {
      main(
          arrayOf("-s", tempFile.absolutePath)
      )
    }
  }
}
