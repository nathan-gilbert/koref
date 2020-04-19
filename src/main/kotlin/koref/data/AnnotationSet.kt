package koref.data

import java.io.Serializable
import java.util.UUID

/**
 * AnnotationSet is used to store annotations.
 *
 * There should be no duplicate annotations in an annotation set.
 *
 * @property name - the name of the set
 */
class AnnotationSet(val name: String) : MutableCollection<Annotation>, Serializable {

  private val annotations: MutableList<Annotation> = mutableListOf()

  override val size: Int
    get() = annotations.size

  /**
   *
   *
   * @param element
   * @return true if set contains element
   */
  override fun contains(element: Annotation): Boolean {
    return annotations.contains(element)
  }

  override fun containsAll(elements: Collection<Annotation>): Boolean {
    elements.forEach {
      if (!annotations.contains(it)) return false
    }
    return true
  }

  override fun isEmpty(): Boolean {
    return annotations.isEmpty()
  }

  override fun add(element: Annotation): Boolean {
    if (annotations.contains(element)) return false
    annotations.add(element)
    return true
  }

  override fun addAll(elements: Collection<Annotation>): Boolean {
    var anyFalse = false
    elements.forEach {
      if (!add(it)) { anyFalse = true }
    }
    return !anyFalse
  }

  override fun clear() {
    annotations.clear()
  }

  override fun iterator(): MutableIterator<Annotation> = annotations.listIterator()

  override fun remove(element: Annotation): Boolean {
    return annotations.remove(element)
  }

  override fun removeAll(elements: Collection<Annotation>): Boolean {
    var anyFalse = false
    elements.forEach { if (!remove(it)) { anyFalse = true} }
    return !anyFalse
  }

  override fun retainAll(elements: Collection<Annotation>): Boolean {
    val removeAnnotations = mutableListOf<Annotation>()
    annotations.forEach { ann -> if (!elements.contains(ann)) removeAnnotations.add(ann) }
    removeAnnotations.forEach {
      annotations.remove(it)
    }
    return true
  }
}
