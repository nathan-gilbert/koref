package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig

class SimpleWhiteSpace(override val annotationName: String,
                       config: SystemConfig,
                       files: ArrayList<Document>)
  : Tokenizer(config, files) {

  private var currentOffset = 0

  /**
   * @param doc
   * @param text
   */
  override fun getStartOffset(doc: Document, text: String): Int {
    val start = doc.getText().indexOf(text, currentOffset)
    currentOffset = start + text.length
    return start
  }

  /**
   * @param doc - the document to tokenize
   */
  override fun tokenize(doc: Document) {
    val tokens = doc.getText().split("\\s".toRegex())
    tokens.forEach {
      val start = getStartOffset(doc, it)
      annotations.add(Annotation(AnnotationType.TOKEN, start, start+it.length, it))
    }

    doc.annotations[AnnotationType.TOKEN] = annotations
    writeAnnotationsToFile(doc.getDocumentDirectory())
    resetOffset()
  }

  /**
   * Run the training documents
   */
  override fun runTrain() {
    if ("tokenizer" in config.getPreprocessors())
      files.forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTrainingDir()}/${it.name}!")
        tokenize(it)
        annotations.clear()
      }
  }

  override fun runTest() {
    if ("tokenizer" in config.getPreprocessors())
      files.forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTestingDir()}/${it.name}!")
        tokenize(it)
      }
  }

  override fun runTuning() {
    if ("tokenizer" in config.getPreprocessors())
      files.forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTuningDir()}/${it.name}!")
        tokenize(it)
      }
  }

  private fun resetOffset() {
    currentOffset = 0
  }
}
