package koref.data

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

internal class AnnotationTest {

  private companion object {
    const val sampleText = "My sample text"
  }

  @Test
  fun getLength() {
    val ann = Annotation("sentence", 0, sampleText.length, sampleText)
    assertThat(ann.length).isEqualTo(sampleText.length)
  }

  @Test
  fun testToString() {
  }

  @Test
  fun testEquals() {
  }

  @Test
  fun overlaps() {
  }

  @Test
  fun testOverlaps() {
  }

  @Test
  fun covers() {
  }

  @Test
  fun testCovers() {
  }

  @Test
  fun covered() {
  }

  @Test
  fun properCovers() {
  }

  @Test
  fun testProperCovers() {
  }

  @Test
  fun spanSize() {
  }

  @Test
  fun compareSpan() {
  }

  @Test
  fun testCompareSpan() {
  }

  @Test
  fun compareTo() {
  }
}