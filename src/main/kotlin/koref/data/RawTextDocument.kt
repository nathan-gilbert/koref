package koref.data

class RawTextDocument(name: String, private val text: String) : Document(name) {

  override fun getText():String {
    return text
  }
}
