package koref.preprocessors.tokenizers

import koref.data.AnnotationType
import koref.preprocessors.PreprocessorType
import koref.utils.KorefTests
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class OpenNLPSentenceTokenizerTest : KorefTests() {

  @Test
  fun `test tokenizer type`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir)
    val doc = getRawTextDoc(tempDir.toString())
    val ot = OpenNLPTokenizer("tokens", config, arrayListOf(doc))
    Assertions.assertThat(ot.type).isEqualTo(PreprocessorType.TOKENIZER)
  }

  @Test
  fun `test run`(@TempDir tempDir: Path) {
    val config = getConfig(tempDir)
    val doc = getRawTextDoc(tempDir.toString())
    val ot = OpenNLPTokenizer("tokens", config, arrayListOf(doc))
    ot.run(doc)
    Assertions.assertThat(doc.annotations.size).isEqualTo(1)
    Assertions.assertThat(doc.annotations[AnnotationType.TOKEN]?.size).isEqualTo(6)
  }
}