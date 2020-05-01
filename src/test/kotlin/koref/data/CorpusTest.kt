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
  fun `get corpus files`() {
    val corpus = Corpus("brown", listOf("file1"))
    assertThat(corpus.files.size).isEqualTo(1)
  }
}