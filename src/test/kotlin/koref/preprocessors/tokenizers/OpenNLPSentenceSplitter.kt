package koref.preprocessors.tokenizers

import koref.data.Document
import koref.utils.SystemConfig
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class OpenNLPSentenceSplitter(override val annotationName: String,
                       config: SystemConfig,
                       files: ArrayList<Document>)
  : Tokenizer(config, files) {

  /*
  private var splitter: SentenceDetectorME

  init {
    val modelIn: InputStream = FileInputStream("${config.getModelDirectory()}${File.separator}en-sent.bin")
    val model = SentenceModel(modelIn)
    splitter = SentenceDetectorME(model)
  }
 */

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
