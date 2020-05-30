package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel

class OpenNLPSentenceSplitter(override val annotationName: String,
                       config: SystemConfig,
                       files: ArrayList<Document>)
  : Tokenizer(config, files) {

  private var splitter: SentenceDetectorME

  init {
    val modelIn: InputStream = FileInputStream("${config.getModelDirectory()}${File.separator}en-sent.bin")
    val model = SentenceModel(modelIn)
    splitter = SentenceDetectorME(model)
  }

  override fun run(doc: Document) {
    val sentences = splitter.sentDetect(doc.getText())
    sentences.forEach {
      val spans = splitter.sentPosDetect(it.toString())
      annotations.add(Annotation(AnnotationType.SENTENCE, spans[0].start, spans[0].end, it.toString()))
    }
  }

  override fun getStartOffset(doc: Document, text: String): Int {
    TODO("Not yet implemented")
  }
}
