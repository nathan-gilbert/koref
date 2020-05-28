package koref

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class KorefDriverTest {
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
    tempFile.writeText("""baseDataDir: /Users/nathan/Documents/Data/raw/example
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: example.train.filelist
testDataDir: .
testFileList: example.test.filelist""")

    assertDoesNotThrow { main(
        arrayOf("-s", tempFile.absolutePath)
    ) }
  }

  @Test
  fun `KorefDriver handles other preprocessors`(@TempDir tempDir: Path) {
    val tempFile = File(tempDir.toString(), "test-settings-other-preprocessors.yml")
    tempFile.writeText("""baseDataDir: /Users/nathan/Documents/Data/raw/example
workingDir: /Users/nathan/Projects/koref
trainDataDir: .
trainFileList: example.train.filelist
testDataDir: .
testFileList: example.test.filelist
preprocessors:
  - other
    """)
    assertDoesNotThrow {
      main(
          arrayOf("-s", tempFile.absolutePath)
      )
    }
  }
}
