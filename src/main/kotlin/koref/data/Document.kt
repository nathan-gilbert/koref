package koref.data

/**
 * TODO
 *
 */
abstract class Document(val name: String) {
  val annotations = mutableMapOf<AnnotationType, ArrayList<Annotation>>()
  abstract fun getText(): String
}

