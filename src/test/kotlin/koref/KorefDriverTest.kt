package koref

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File

class KorefDriverTest {
  companion object {
    private val resourcesDirectory = File("src/test/resources").absolutePath
    private val testSettingsNoPre = "$resourcesDirectory/test-settings-no-preprocessors.yml"
    private val testSettingsOtherPre = "$resourcesDirectory/test-settings-other-preprocessors.yml"
  }

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
  fun `KorefDriver handles no preprocessors`() {
    assertDoesNotThrow { main(
        arrayOf("-s", testSettingsNoPre)
    ) }
  }

  @Test
  fun `KorefDriver handles other preprocessors`() {
    assertDoesNotThrow {
      main(
          arrayOf("-s", testSettingsOtherPre)
      )
    }
  }
}
