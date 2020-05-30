package koref.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import koref.data.AnnotationType.PHRASE
import koref.data.AnnotationType.POS
import koref.data.AnnotationType.SENTENCE
import koref.data.AnnotationType.TOKEN
import koref.data.AnnotationType.UNKNOWN
import java.io.Serializable
import java.util.TreeSet
import java.util.UUID

/**
 * The type of annotation
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @property UNKNOWN
 * @property TOKEN - a bare linguistic token
 * @property PHRASE - a combination of tokens
 * @property SENTENCE - a sentence
 * @property POS - part of speech tag
 */
enum class AnnotationType {
  UNKNOWN, TOKEN, PHRASE, SENTENCE, POS
}

/**
 * Annotations are the basic datatype for Koref.
 *
 * This class can hold a variety of data useful for data science & linguistic tabulation.
 * Annotations are defined in term of their type, offset, attributes and id
 *
 * @param type the type of annotation
 * @param startOffset the beginning byte location of the annotation in the document
 * @param endOffset the ending byte location of the annotation in the document
 * @param content the textual content of the annotation
 * @property id a uuid of the annotation
 * @property features the data science related features for this annotation
 * @property properties additional data that may be of interest for this annotation
 * @property tokens array of words from the textual content
 * @property cleanContent cleaned up text content (spaces, newlines stripped)
 * @property length the length of the annotation is the bytespan length
 * @constructor Creates an annotation with defined type, start and end offset and textual content
 */
@Suppress("TooManyFunctions")
class Annotation(
    val type: AnnotationType,
    val startOffset: Int,
    val endOffset: Int,
    val content: String
) : Serializable {

  val id: UUID = UUID.randomUUID()
  var features: MutableMap<String, String?> = mutableMapOf()
  var properties: MutableMap<String, Any?> = mutableMapOf()
  val cleanContent: String = content.replace("\n", "")
  val tokens: List<String> = content.split(" ")
  val length: Int
    get() = endOffset - startOffset

  override fun toString(): String {
    return ("Annotation: id=$id; type=$type; start=$startOffset; end=$endOffset; content=$content")
  }

  fun toJson(): String {
    val mapper = jacksonObjectMapper()
    return mapper.writeValueAsString(this)
  }

  override fun equals(other: Any?): Boolean {
    if (other == null) return false
    val otherAnnotation: Annotation = if (other is Annotation) {
      other
    } else return false

    if (type != otherAnnotation.type) return false
    if (otherAnnotation.startOffset != startOffset) return false
    if (otherAnnotation.endOffset != endOffset) return false
    if (features != otherAnnotation.features) return false
    return id == otherAnnotation.id
  }

  /**
   * Returns if this annotation overlaps spans with another
   *
   * @param ann the other annotation
   * @return boolean
   */
  fun overlaps(ann: Annotation?): Boolean {
    return if (ann == null) false else this.overlaps(ann.startOffset, ann.endOffset)
  }

  /**
   * Checks if this annotation overlaps with a specific span
   *
   * @param start
   * @param end
   * @return boolean
   */
  fun overlaps(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0 || end <= startOffset) return false
    return start <= endOffset
  }

  /**
   * Returns true if this annotation completely covers the span of the other annotation
   *
   * @param ann the other annotation
   * @return boolean
   */
  fun covers(ann: Annotation?): Boolean {
    return if (ann == null) false else this.covers(ann.startOffset, ann.endOffset)
  }

  /**
   * Returns true if this annotation completely covers the other span
   *
   * @param start
   * @param end
   * @return boolean
   */
  fun covers(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return startOffset <= start && end <= endOffset
  }

  /**
   * Returns true if this annotation is completely covered by the span
   *
   * @param start
   * @param end
   * @return boolean
   */
  fun covered(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return startOffset >= start && end >= endOffset
  }

  /**
   * Returns true if this annotation is completely covered by the other annotation's span
   *
   * @param ann the other annotation
   * @return
   */
  fun properCovers(ann: Annotation?): Boolean {
    return if (ann == null) false else this.properCovers(ann.startOffset, ann.endOffset)
  }

  /**
   *  Returns true if this annotation is completely covered by the span
   *
   * @param start
   * @param end
   * @return boolean
   */
  fun properCovers(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return (start > startOffset && end < endOffset)
  }

  /**
   * Compares annotation offsets
   *
   * @return 0 is the two annotations have the same span, -1 if **this** comes first
   * (starts or ends before A), and -1 if ann comes first.
   */
  fun compareSpan(ann: Annotation?): Int {
    if (ann == null) throw NullPointerException()
    return compareSpan(ann.startOffset, ann.endOffset)
  }

  /**
   * Compares annotation offsets
   *
   * @return 0 is the two annotations have the same span, -1 if **this** comes first (starts or
   * ends before ann), and -1 if ann comes first.
   */
  @Suppress("ReturnCount")
  fun compareSpan(startOffset: Int, endOffset: Int): Int {
    if (this.startOffset < startOffset || this.endOffset < endOffset) return -1
    if (this.startOffset > startOffset || this.endOffset > endOffset) return 1
    return 0
  }

  /**
   * Compares 2 annotations on every field except uuid
   *
   * @param other the other annotation
   * @return -1, 1, 0 (used for comparison algos)
   */
  @Suppress("ReturnCount")
  fun compareTo(other: Annotation?): Int {
    if (other == null) throw NullPointerException()
    val spanCompare = this.compareSpan(other)
    if (spanCompare != 0) return spanCompare
    val typeCompare = type.compareTo(other.type)
    if (typeCompare != 0) return typeCompare
    if (other.features.size > features.size) return -1
    if (other.features.size < features.size) return 1
    // compare on key by key basis
    val aKeys = TreeSet(other.features.keys)
    val mKeys = TreeSet(features.keys)
    val ai: Iterator<String> = aKeys.iterator()
    val mi: Iterator<String> = mKeys.iterator()
    while (ai.hasNext()) {
      val aK = ai.next()
      val mK = mi.next()
      if (mK.compareTo(aK) != 0) return mK.compareTo(aK)
      val aV = other.features[aK]
      val mV = features[mK]
      if (aV == null && mV == null) {
        continue
      }
      if (aV == null) return 1
      if (mV == null) return -1
      if (mV.compareTo(aV) != 0) return mV.compareTo(aV)
    }
    return 0
  }

  override fun hashCode(): Int {
    var result = type.hashCode()
    result = 31 * result + startOffset
    result = 31 * result + endOffset
    result = 31 * result + content.hashCode()
    result = 31 * result + id.hashCode()
    result = 31 * result + features.hashCode()
    result = 31 * result + cleanContent.hashCode()
    result = 31 * result + tokens.hashCode()
    return result
  }
}
