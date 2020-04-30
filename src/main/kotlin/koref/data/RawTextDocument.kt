package koref.data

import java.io.File

class RawTextDocument(name: String, rawText: String? = null) : Document(name) {
  private var text = rawText ?: ""

  fun readRawTextFile(inFile: String) {
    text = File(inFile).readText()
  }

  override fun getText():String {
    return text
  }
}
