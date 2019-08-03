package koref

import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow

class KorefDriverTest {
  @Test
  fun `koref driver does not throw exception`() {
    assertDoesNotThrow { main(arrayOf()) }
  }
}
