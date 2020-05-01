package koref.preprocessors.tokenizers

import koref.data.Document
import koref.utils.SystemConfig

class SimpleWhiteSpace(override val annotationName: String,
                       config: SystemConfig,
                       files: ArrayList<Document>)
  : Tokenizer(config, files) {

  override fun runTrain() {
    if ("tokenizer" in config.getPreprocessors())
      config.getTrainingFiles().forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTrainingDir()}/$it!")
      }
  }

  override fun runTest() {
    if ("tokenizer" in config.getPreprocessors())
      config.getTestingFiles().forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTestingDir()}/$it!")
      }
  }

  override fun runTuning() {
    if ("tokenizer" in config.getPreprocessors())
      config.getTuningFiles().forEach {
        println("${SimpleWhiteSpace::class.simpleName}-> I'm running in ${config.getTuningDir()}/$it!")
      }
  }
}
