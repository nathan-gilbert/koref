package koref.data

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

internal class CorpusTest {

  @Test
  fun getName() {
    val corpus = Corpus("brown")
    assertThat(corpus.name).isEqualTo("brown")
  }
}