package koref.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AnnotationTest {

  private companion object {
    const val sampleWord = "sample"
    const val sampleText = "My sample text"
    const val sampleWordText = "sample"
    const val sampleTextNewlines = "My\nsample\ntext"
    const val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque " +
        "sed turpis in mauris pellentesque gravida. In enim tortor, ornare ac efficitur vitae, f" +
        "inibus semper turpis. In feugiat est tortor, non vestibulum leo consectetur at. Nullam " +
        "id lobortis leo, lobortis faucibus mauris. Nam diam enim, placerat in velit ut, imperdi" +
        "et imperdiet purus. Integer pretium ligula sit amet nunc imperdiet finibus. Integer por" +
        "ttitor, risus ac sagittis hendrerit, lorem nisl pellentesque enim, sit amet pharetra ip" +
        "sum nibh sed nisl. Fusce consequat luctus erat sagittis convallis. Nulla facilisi."
  }

  @Test
  fun `annotation length is equal to bytespan`() {
    val ann = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann.length).isEqualTo(sampleText.length)
  }

  @Test
  fun `creates proper string representation`() {
    val ann = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val annStr = ann.toString()
    val expectStr = "Annotation: id=${ann.id}; type=${ann.type}; start=${ann.startOffset}; end=${ann.endOffset}"
    assertThat(annStr).isEqualTo(expectStr)
  }

  @Test
  fun `equals works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1 == ann2).isFalse
    assertThat(ann1 == ann1).isTrue
    assertThat(ann1.equals(0)).isFalse
    @Suppress("EqualsNullCall")
    assertThat(ann1.equals(null)).isFalse
    val ann3 = Annotation(AnnotationType.TOKEN, 3, sampleWord.length, sampleWord)
    assertThat(ann1.equals(ann3)).isFalse
    val ann4 = Annotation(AnnotationType.TOKEN, -1, -1, "")
    assertThat(ann3.equals(ann4)).isFalse
    val ann5 = Annotation(AnnotationType.TOKEN, -1, 10, "")
    assertThat(ann4.equals(ann5)).isFalse
    val ann6 = Annotation(AnnotationType.TOKEN, -1, -1, "")
    ann6.features["myFeat"] = "0"
    assertThat(ann4.equals(ann6)).isFalse
  }

  @Test
  fun `content manipulation works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleTextNewlines.length, sampleTextNewlines)
    assertThat(ann1.content).isEqualTo(sampleTextNewlines)
    assertThat(ann1.cleanContent).isEqualTo(sampleTextNewlines.replace("\n", ""))
    assertThat(ann1.tokens).isEqualTo(sampleTextNewlines.split(" "))
  }

  @Test
  fun `overlaps works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.overlaps(null)).isFalse
    assertThat(ann1.overlaps(ann2)).isTrue
    assertThat(ann1.overlaps(-1, 10)).isFalse
    assertThat(ann1.overlaps(1, -10)).isFalse
    val ann3 = Annotation(AnnotationType.TOKEN, 50, longText.length, longText)
    assertThat(ann1.overlaps(ann3)).isFalse
    assertThat(ann3.overlaps(ann1)).isFalse
  }

  @Test
  fun `covers works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.covers(ann2)).isTrue
    assertThat(ann1.covers(null)).isFalse
    assertThat(ann1.covers(-1, 10)).isFalse
    assertThat(ann1.covers(1, -10)).isFalse
    assertThat(ann1.covers(3, 5)).isTrue
    assertThat(ann1.covers(3, 50)).isFalse
    val ann3 = Annotation(AnnotationType.TOKEN, 50, longText.length, longText)
    assertThat(ann3.covers(30, 60)).isFalse
  }

  @Test
  fun `covered works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.covered(ann2.startOffset, ann2.endOffset)).isTrue
    assertThat(ann1.covered(-1, 10)).isFalse
    assertThat(ann1.covered(1, -110)).isFalse
    assertThat(ann1.covered(3, 5)).isFalse
    assertThat(ann1.covered(3, 50)).isFalse
    val ann3 = Annotation(AnnotationType.TOKEN, 50, longText.length, longText)
    assertThat(ann3.covered(30, 60)).isFalse
  }

  @Test
  fun `proper covers works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.properCovers(null)).isFalse
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.properCovers(ann2)).isFalse
    val ann3 = Annotation(AnnotationType.TOKEN, 3, sampleWord.length, sampleWord)
    assertThat(ann1.properCovers(ann3)).isTrue
    assertThat(ann1.properCovers(-1, 10)).isFalse
    assertThat(ann1.properCovers(1, -110)).isFalse
    assertThat(ann1.properCovers(3, 5)).isTrue
    assertThat(ann1.properCovers(3, 50)).isFalse
    val ann4 = Annotation(AnnotationType.TOKEN, 50, longText.length, longText)
    assertThat(ann4.properCovers(30, 60)).isFalse
  }

  @Test
  fun `compareSpan works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThrows<NullPointerException> {
      ann1.compareSpan(null)
    }
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.compareSpan(ann2)).isEqualTo(0)
    val ann3 = Annotation(AnnotationType.TOKEN, 3, sampleWordText.length, sampleWordText)
    assertThat(ann1.compareSpan(ann3)).isEqualTo(-1)
    assertThat(ann3.compareSpan(0, 5)).isEqualTo(1)
    assertThat(ann3.compareSpan(3, 5)).isEqualTo(1)
    assertThat(ann3.compareSpan(3, 50)).isEqualTo(-1)
  }

  @Test
  fun `compareTo works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThrows<NullPointerException> {
      ann1.compareTo(null)
    }
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    assertThat(ann1.compareTo(ann2)).isEqualTo(0)
    val ann3 = Annotation(AnnotationType.TOKEN, 3, sampleWordText.length, sampleWordText)
    assertThat(ann1.compareTo(ann3)).isEqualTo(-1)
    ann1.features["myFeat"] = "0"
    val ann4 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    ann4.features["myFeat"] = "0"
    assertThat(ann1.compareTo(ann4)).isEqualTo(0)
    ann4.features["myFeat2"] = "1"
    assertThat(ann1.compareTo(ann4)).isEqualTo(-1)
    val ann5 = Annotation(AnnotationType.TOKEN, 0, sampleText.length, sampleText)
    assertThat(ann1.compareTo(ann5)).isEqualTo(2)
  }

  @Test
  fun `compareTo features tests`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    ann1.features = mutableMapOf()
    assertThat(ann1.features.size).isEqualTo(0)
    ann1.features["myFeat"] = null
    val ann2 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    ann2.features["myFeat"] = null
    assertThat(ann1.compareTo(ann2)).isEqualTo(0)
    ann1.features["myFeat"] = "0"
    ann2.features["myFeat"] = "0"
    ann2.features["myFeat2"] = "1"
    assertThat(ann2.compareTo(ann1)).isEqualTo(1)
    ann2.features = mutableMapOf()
    ann2.features["myFeat2"] = "1"
    assertThat(ann2.compareTo(ann1)).isEqualTo(1)

    ann1.features = mutableMapOf()
    ann1.features["myFeat"] = "0"
    ann2.features = mutableMapOf()
    ann2.features["myFeat"] = null
    assertThat(ann1.compareTo(ann2)).isEqualTo(1)

    ann1.features = mutableMapOf()
    ann1.features["myFeat"] = null
    ann2.features = mutableMapOf()
    ann2.features["myFeat"] = "0"
    assertThat(ann1.compareTo(ann2)).isEqualTo(-1)
    ann1.features["myFeat"] = "1"
    assertThat(ann1.compareTo(ann2)).isEqualTo(1)
  }

  @Test
  fun `hashCode works`() {
    val ann1 = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    val ann2 = ann1
    assertThat(ann1 == ann2).isTrue
    assertThat(ann1.hashCode()).isEqualTo(ann2.hashCode())
  }
}