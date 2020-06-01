package koref.preprocessors.tokenizers

import edu.stanford.nlp.process.CoreLabelTokenFactory
import edu.stanford.nlp.process.PTBTokenizer
import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig
import java.io.FileReader

class StanfordTokenizer(
    override val annotationName: String,
    config: SystemConfig,
    files: ArrayList<Document>
) :
    Tokenizer(config, files) {

  override fun run(doc: Document) {
    val tokenizer = PTBTokenizer(FileReader(doc.getDocumentDirectory()), CoreLabelTokenFactory(), "")

    while (tokenizer.hasNext()) {
      val label = tokenizer.next()
      val ann = Annotation(AnnotationType.TOKEN, label.beginPosition(), label.endPosition(), label.toString())
      annotations.add(ann)
    }
    doc.annotations[AnnotationType.TOKEN] = annotations
    writeAnnotationsToFile(doc.getDocumentDirectory())
  }
}
