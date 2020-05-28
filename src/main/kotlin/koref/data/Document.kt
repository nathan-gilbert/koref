package koref.data

import java.io.File

abstract class Document(val name: String, val baseDir: String) {
  val annotations = mutableMapOf<AnnotationType, ArrayList<Annotation>>()
  abstract fun getText(): String
  fun getDocumentDirectory(): String = "$baseDir${File.separator}$name"
}

