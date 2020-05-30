package koref.preprocessors.tokenizers

import koref.data.Document
import koref.preprocessors.Preprocessor
import koref.preprocessors.PreprocessorType
import koref.utils.SystemConfig

abstract class Tokenizer(config: SystemConfig, files: ArrayList<Document>) :
  Preprocessor(config, files) {
  override val type = PreprocessorType.TOKENIZER
  abstract fun run(doc: Document)
}
