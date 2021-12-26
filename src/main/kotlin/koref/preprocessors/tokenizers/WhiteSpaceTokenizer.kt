package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig

class WhiteSpaceTokenizer(
  override val annotationName: String,
  config: SystemConfig,
  files: ArrayList<Document>
) : Tokenizer(config, files) {

  private var currentOffset = 0

  /**
   * @param doc - the document to tokenize
   */
  override fun run(doc: Document) {
    val tokens = doc.getText().split("\\s".toRegex())
    tokens.forEach {
      val start = getStartOffset(doc, it)
      annotations.add(Annotation(AnnotationType.TOKEN, start, start + it.length, it))
    }

    doc.annotations[AnnotationType.TOKEN] = annotations
    writeAnnotationsToFile(doc.getDocumentDirectory())
    resetOffset()
  }

  /**
   * @param doc
   * @param text
   */
  private fun getStartOffset(doc: Document, text: String): Int {
    val start = doc.getText().indexOf(text, currentOffset)
    currentOffset = start + text.length
    return start
  }

  private fun resetOffset() {
    currentOffset = 0
  }
}
