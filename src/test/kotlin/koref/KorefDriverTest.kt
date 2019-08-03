package koref

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class KorefDriverTest {
  @Test
  fun `KorefDriver does not throw exception`() {
    assertDoesNotThrow { main(arrayOf()) }
  }
}
