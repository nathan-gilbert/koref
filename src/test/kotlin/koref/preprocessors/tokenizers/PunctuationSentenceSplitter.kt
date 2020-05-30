package koref.preprocessors.tokenizers

import koref.data.Document
import koref.utils.SystemConfig

class PunctuationSentenceSplitter(override val annotationName: String,
                              config: SystemConfig,
                              files: ArrayList<Document>)
  : Tokenizer(config, files) {

  override fun run(doc: Document) {
    TODO("Not yet implemented")
  }

  override fun runTrain() {
    TODO("Not yet implemented")
  }

  override fun runTest() {
    TODO("Not yet implemented")
  }

  override fun runTuning() {
    TODO("Not yet implemented")
  }

  override fun getStartOffset(doc: Document, text: String): Int {
    TODO("Not yet implemented")
  }
}
