package koref.data

/**
 * TODO
 *
 */
abstract class Document(val name: String) {
  val annotations = mutableMapOf<String, ArrayList<Annotation>>()
  abstract fun getText(): String
}

