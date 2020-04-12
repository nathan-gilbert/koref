package koref.data

import java.io.Serializable
import java.util.TreeSet

/**
 * An Annotation is the basic data type of Koref.
 */
class Annotation(private val type: String?,
                 private val startOffset: Int,
                 private val endOffset: Int,
                 private val content: String?) : Serializable {

  // TODO: make this a uuid
  // Unique id for this Annotation
  private val id: Int = 0

  //Data science features for this annotation
  private var features: MutableMap<String, String?>? = null

  // A holder for the text content of the annotation span (cleaned up string)
  private var textContent: String? = content?.replace(" ", "")

  // A holder for the word content of the annotation span
  private var words: Array<String>? = null

  val length: Int
    get() = endOffset - startOffset

  override fun toString(): String {
    return ("AnnotationImpl: id=" + id + "; type=" + type + "; features=" + features + "; start=" + startOffset +
        "; end=" + endOffset + System.getProperty("line.separator"))
  }

  override fun equals(other: Any?): Boolean {
    if (other == null) return false
    val otherAnnotation: Annotation = if (other is Annotation) {
      other
    } else return false

    // If their types are not equals then return false
    if ((type == null) xor (otherAnnotation.type == null)) return false
    if (type != null && type != otherAnnotation.type) return false

    // If their start offset is not the same then return false
    if ((startOffset < 0) xor (otherAnnotation.startOffset < 0)) return false
    if (startOffset >= 0) if (otherAnnotation.startOffset != startOffset) return false

    // If their end offset is not the same then return false
    if ((endOffset < 0) xor (otherAnnotation.endOffset < 0)) return false
    if (endOffset >= 0) if (otherAnnotation.endOffset != endOffset) return false

    // If their featureMaps are not equals then return false
    if ((features == null) xor (otherAnnotation.features == null)) return false
    if (features != null && features != otherAnnotation.features) return false

    return id != otherAnnotation.id
  }

  /*
   * Does this annotation and annotation A overlap.
   */
  fun overlaps(A: Annotation?): Boolean {
    return if (A == null) false else this.overlaps(A.startOffset, A.endOffset)
  }

  /*
   * Does this annotation overlap with the designated span.
   */
  fun overlaps(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    if (end <= startOffset) return false
    return start >= endOffset
  }

  /**
   * This method is used to determine if this annotation covers completely in span annotation A
   */
  fun covers(A: Annotation?): Boolean {
    return if (A == null) false else this.covers(A.startOffset, A.endOffset)
  } // covers

  /**
   * This method is used to determine if this annotation covers completely the designated span
   */
  fun covers(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return startOffset <= start && end <= endOffset
  }

  /**
   * This method is used to determine if this annotation is covered completely the designated span
   */
  fun covered(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return startOffset >= start && end >= endOffset
  }

  fun properCovers(A: Annotation?): Boolean {
    return if (A == null) false else this.properCovers(A.startOffset, A.endOffset)
  }

  /*
   * This method is used to determine if this annotation properly covers the span given.
   */
  fun properCovers(start: Int, end: Int): Boolean {
    if (start < 0 || end < 0) return false
    return (startOffset < start && end <= endOffset
        || startOffset <= start && end < endOffset)
  }

  /**
   * The length of the annotation span.
   *
   * @return the length of the span.
   */
  fun spanSize(): Int {
    return endOffset - startOffset
  }

  /**
   * Compares annotation offsets
   *
   * @return 0 is the two annotations have the same span, -1 if **this** comes first
   * (starts or ends before A), and -1 if A comes first.
   */
  fun compareSpan(A: Annotation?): Int {
    if (A == null) throw NullPointerException()
    if (startOffset < A.startOffset) return -1
    if (startOffset > A.startOffset) return 1

    // the start offsets are equal
    if (endOffset < A.endOffset) return -1
    return if (endOffset > A.endOffset) 1 else 0
  }

  /**
   * Compares annotation offsets
   *
   * @return 0 is the two annotations have the same span, -1 if **this** comes first (starts or
   * ends before A), and -1 if A comes first.
   */
  fun compareSpan(startOffset: Int, endOffset: Int): Int {
    if (this.startOffset < startOffset) return -1
    if (this.startOffset > startOffset) return 1

    // the start offsets are equal
    if (this.endOffset < endOffset) return -1
    return if (this.endOffset > endOffset) 1 else 0
  }

  fun compareTo(other: Annotation?): Int {
    val spanCompare = this.compareSpan(other)
    if (spanCompare != 0) return spanCompare
    val typeCompare = type!!.compareTo(other?.type!!)
    if (typeCompare != 0) return typeCompare
    if (other.features!!.size > features!!.size) return -1
    if (other.features!!.size < features!!.size) return 1
    val aKeys = TreeSet(other.features!!.keys)
    val mKeys = TreeSet(features!!.keys)
    val ai: Iterator<String> = aKeys.iterator()
    val mi: Iterator<String> = mKeys.iterator()
    while (ai.hasNext()) {
      val aK = ai.next()
      val mK = mi.next()
      if (mK.compareTo(aK) != 0) return mK.compareTo(aK)
      val aV = other.features!![aK]
      val mV = features!![mK]
      if (aV == null && mV == null) {
        continue
      }
      if (aV == null) return 1
      if (mV == null) return -1
      if (mV.compareTo(aV) != 0) return mV.compareTo(aV)
    }
    val idCompare = Integer.valueOf(id).compareTo(other.id)
    return if (idCompare != 0) idCompare else 0
  }

  override fun hashCode(): Int {
    var result = id
    result = 31 * result + (type?.hashCode() ?: 0)
    result = 31 * result + (features?.hashCode() ?: 0)
    result = 31 * result + startOffset
    result = 31 * result + endOffset
    result = 31 * result + (content?.hashCode() ?: 0)
    result = 31 * result + (textContent?.hashCode() ?: 0)
    result = 31 * result + (words?.contentHashCode() ?: 0)
    return result
  }
}
