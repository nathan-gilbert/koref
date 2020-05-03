package koref.data

import java.nio.file.Path

class Corpus(val name: String, val baseDir: String, val files: List<String>) {
  val docs = ArrayList<Document>()

  fun createDocuments() {
    files.forEach {
      docs.add(RawTextDocument(it, baseDir))
    }
  }
}
