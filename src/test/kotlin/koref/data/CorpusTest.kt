package koref.data

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

internal class CorpusTest {

  @Test
  fun `get corpus name`() {
    val corpus = Corpus("brown", listOf(""))
    assertThat(corpus.name).isEqualTo("brown")
  }

  @Test
  fun ``() {
    
  }
}