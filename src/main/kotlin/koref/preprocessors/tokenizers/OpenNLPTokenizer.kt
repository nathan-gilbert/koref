package koref.preprocessors.tokenizers

import koref.data.Annotation
import koref.data.AnnotationType
import koref.data.Document
import koref.utils.SystemConfig
import opennlp.tools.tokenize.TokenizerME
import opennlp.tools.tokenize.TokenizerModel
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class OpenNLPTokenizer(
    override val annotationName: String,
    config: SystemConfig,
    files: ArrayList<Document>
) :
    Tokenizer(config, files) {

  private var tokenizer: TokenizerME

  init {
    val modelLoc = "${config.getModelDir()}${File.separator}en-token.bin"
    val inputStream: InputStream = FileInputStream(modelLoc)
    val tokenModel = TokenizerModel(inputStream)
    tokenizer = TokenizerME(tokenModel)
  }

  override fun run(doc: Document) {
    val tokens = tokenizer.tokenize(doc.getText())
    val spans = tokenizer.tokenizePos(doc.getText())
    val probabilities = tokenizer.tokenProbabilities

    tokens.forEachIndexed { index, tok ->
      val ann = Annotation(AnnotationType.TOKEN, spans[index].start, spans[index].end, tok.toString())
      ann.properties["probability"] = probabilities[index]
      annotations.add(ann)
    }

    doc.annotations[AnnotationType.TOKEN] = annotations
    writeAnnotationsToFile(doc.getDocumentDirectory())
  }
}
