package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig
import opennlp.tools.sentdetect.SentenceDetectorME
import opennlp.tools.sentdetect.SentenceModel
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class OpenNLPSentenceSplitter(
    override val annotationName: String,
    config: SystemConfig,
    files: ArrayList<Document>
) :
    Tokenizer(config, files) {

  private var splitter: SentenceDetectorME

  init {
    val modelLoc = "${config.getModelDir()}${File.separator}en-sent.bin"
    val modelIn: InputStream = FileInputStream(modelLoc)
    val model = SentenceModel(modelIn)
    splitter = SentenceDetectorME(model)
  }

  override fun run(doc: Document) {
    val sentences = splitter.sentDetect(doc.getText())
    val spans = splitter.sentPosDetect(doc.getText())
    sentences.forEachIndexed { index, element ->
      annotations.add(Annotation(AnnotationType.SENTENCE, spans[index].start, spans[index].end, element.toString()))
    }

    doc.annotations[AnnotationType.SENTENCE] = annotations
    writeAnnotationsToFile(doc.getDocumentDirectory())
  }
}
