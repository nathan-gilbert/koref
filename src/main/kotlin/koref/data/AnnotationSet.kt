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

  private val annotations: MutableMap<UUID, Annotation> = mutableMapOf()

  override val size: Int
    get() = annotations.size

  override fun contains(element: Annotation): Boolean {
    TODO("Not yet implemented")
  }

  override fun containsAll(elements: Collection<Annotation>): Boolean {
    TODO("Not yet implemented")
  }

  override fun isEmpty(): Boolean {
    return annotations.isEmpty()
  }

  override fun add(element: Annotation): Boolean {
    if (annotations.keys.contains(element.id))
      return false
    annotations[element.id] = element
    return true
  }

  override fun addAll(elements: Collection<Annotation>): Boolean {
    TODO("Not yet implemented")
  }

  override fun clear() {
    annotations.clear()
  }

  override fun iterator(): MutableIterator<Annotation> {
    TODO("Not yet implemented")
  }

  override fun remove(element: Annotation): Boolean {
    val removed = annotations.remove(element.id)
    if (removed != null)
      return true
    return false
  }

  override fun removeAll(elements: Collection<Annotation>): Boolean {
    TODO("Not yet implemented")
  }

  override fun retainAll(elements: Collection<Annotation>): Boolean {
    TODO("Not yet implemented")
  }

}

