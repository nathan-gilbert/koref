package koref.preprocessors.tokenizers

import koref.utils.SystemConfig

class SimpleWhiteSpace(override val annotationName: String, config: SystemConfig) : Tokenizer(config) {
  override fun run() {
    if (annotationName in config.getPreprocessors())
      println("${SimpleWhiteSpace::class.simpleName}-> I'm running!!")
  }
}
