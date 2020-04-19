package koref.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AnnotationSetTest {

  companion object {
    private const val sampleText = "My sample text"
    private val ann = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
    private val ann2 = Annotation(AnnotationType.SENTENCE, 50, 50 + sampleText.length, sampleText)
    private val ann3 = Annotation(AnnotationType.SENTENCE, 100, 100 + sampleText.length, sampleText)
  }

  @Test
  fun `can create a set`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    assertThat(sentences.size).isEqualTo(1)
    sentences.add(ann)
    assertThat(sentences.size).isEqualTo(1)
    assertThat(sentences.name).isEqualTo("sentences")
  }

  @Test
  fun `contains works`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    assertThat(sentences.contains(ann)).isEqualTo(true)
    assertThat(sentences.contains(ann2)).isEqualTo(false)
  }

  @Test
  fun `containsAll works`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    assertThat(sentences.containsAll(listOf(ann))).isEqualTo(true)
    assertThat(sentences.containsAll(listOf(ann, ann2))).isEqualTo(false)
  }

  @Test
  fun `isEmpty works`() {
    val sentences = AnnotationSet("sentences")
    assertThat(sentences.isEmpty()).isEqualTo(true)
    sentences.add(ann)
    assertThat(sentences.isEmpty()).isEqualTo(false)
  }

  @Test
  fun `can add all elements from another set`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    sentences.add(ann2)
    sentences.add(ann3)
    val sentences2 = AnnotationSet("sentences")
    sentences2.addAll(sentences)
    assertThat(sentences2.size).isEqualTo(3)
    val otherSentences = AnnotationSet("more_sentences")
    otherSentences.add(ann3)
    otherSentences.addAll(sentences)
    assertThat(otherSentences.size).isEqualTo(3)
  }

  @Test
  fun `clear empties a set`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    sentences.add(ann2)
    sentences.clear()
    assertThat(sentences.size).isEqualTo(0)
  }

  @Test
  fun `remove works`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    sentences.remove(ann)
    assertThat(sentences.size).isEqualTo(0)
    val result = sentences.remove(ann2)
    assertThat(result).isEqualTo(false)
  }

  @Test
  fun `removeAll works`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    var result = sentences.removeAll(listOf(ann, ann2))
    assertThat(result).isEqualTo(false)
    sentences.add(ann3)
    result = sentences.removeAll(listOf(ann3))
    assertThat(result).isEqualTo(true)
  }

  @Test
  fun `retainAll works`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    sentences.add(ann2)
    sentences.retainAll(listOf(ann2))
    assertThat(sentences.size).isEqualTo(1)
    assertThat(sentences.contains(ann2)).isEqualTo(true)
  }

  @Test
  fun `can iterate over set`() {
    val sentences = AnnotationSet("sentences")
    assertThat(sentences.size).isEqualTo(0)

    sentences.add(ann)
    sentences.add(ann2)
    sentences.add(ann3)

    for (sentence in sentences) {
      assertThat(sentence.length).isEqualTo(sampleText.length)
    }
  }
}