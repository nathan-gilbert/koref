package koref.data

import java.io.File

class RawTextDocument(name: String, rawText: String? = null) : Document(name) {
  private var text = rawText ?: ""

  fun readRawTextFile(inFile: String) {
    val lines = File(inFile).bufferedReader().readLines()
    text = lines.joinToString()
  }

  override fun getText():String {
    return text
  }
}
