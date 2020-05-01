package koref.data

class Corpus(val name: String, val files: List<String>) {
  val docs = ArrayList<Document>()

  fun createDocuments() {
    files.forEach {
      docs.add(RawTextDocument(it))
    }
  }
}
