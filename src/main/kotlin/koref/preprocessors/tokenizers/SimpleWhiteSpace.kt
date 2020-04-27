package koref.preprocessors.tokenizers

import koref.utils.SystemConfig

class SimpleWhiteSpace(override val annotationName: String, config: SystemConfig) : Tokenizer(config) {
  override fun runTrain() {
    if ("tokenizer" in config.getPreprocessors())
      config.getTrainingFiles().forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTrainingDir()}/$it!")
      }
  }

  override fun runTest() {
    TODO("Not yet implemented")
  }

  override fun runTuning() {
    TODO("Not yet implemented")
  }
}
