package koref.data

import java.io.File

class RawTextDocument(fileDir: String, baseDir: String, rawText: String? = null) : Document(fileDir, baseDir) {
  private val rawTextFileName = "raw.txt"
  private val text = rawText ?: readRawTextFile("$fileDir${File.separator}$rawTextFileName")

  private fun readRawTextFile(inFile: String): String {
    return File("$baseDir${File.separator}$inFile").readText()
  }

  override fun getText():String {
    return text
  }
}
