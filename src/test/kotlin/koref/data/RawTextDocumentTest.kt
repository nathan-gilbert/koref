package koref.data

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

internal class RawTextDocumentTest {
  companion object {
    private const val text = "This is my test document."
  }

  @Test
  fun `get document name`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.name).isEqualTo("myTest")
  }

  @Test
  fun `get raw document text`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.getText()).isEqualTo(text)
  }

  @Test
  fun `get annotations`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.annotations.keys.size).isEqualTo(0)
  }
}