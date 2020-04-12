package koref.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AnnotationSetTest {

  companion object {
    private const val sampleText = "My sample text"
    private val ann = Annotation(AnnotationType.SENTENCE, 0, sampleText.length, sampleText)
  }

  @Test
  fun `can create a set`() {
    val sentences = AnnotationSet("sentences")
    sentences.add(ann)
    assertThat(sentences.size).isEqualTo(1)
  }

  @Test
  fun `contains works`() {
  }

  @Test
  fun `containsAll works`() {
  }

  @Test
  fun isEmpty() {
    val sentences = AnnotationSet("sentences")
    assertThat(sentences.isEmpty()).isEqualTo(true)
    sentences.add(ann)
    assertThat(sentences.isEmpty()).isEqualTo(false)
  }

  @Test
  fun add() {
  }

  @Test
  fun addAll() {
  }

  @Test
  fun clear() {
  }

  @Test
  operator fun iterator() {
  }

  @Test
  fun remove() {
  }

  @Test
  fun removeAll() {
  }

  @Test
  fun retainAll() {
  }

  @Test
  fun getName() {
  }
}