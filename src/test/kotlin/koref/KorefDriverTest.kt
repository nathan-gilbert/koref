package koref

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

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
}
