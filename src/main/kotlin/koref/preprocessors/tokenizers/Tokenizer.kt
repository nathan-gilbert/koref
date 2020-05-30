package koref.preprocessors.tokenizers

import koref.data.Document
import koref.preprocessors.Preprocessor
import koref.preprocessors.PreprocessorType
import koref.utils.SystemConfig

abstract class Tokenizer(config: SystemConfig, files: ArrayList<Document>) : Preprocessor(config, files) {
  abstract fun run(doc: Document)

  override val type = PreprocessorType.TOKENIZER

  override fun runTrain() {
    if ("tokenizer" in config.getPreprocessors()) {
      files.forEach {
        println("${this::class.simpleName}-> I'm running in ${config.getTrainingDir()}/${it.name}!")
        run(it)
        annotations.clear()
      }
    }
  }

  override fun runTest() {
    if ("tokenizer" in config.getPreprocessors()) {
      files.forEach {
        println("${this::class.simpleName}-> I'm running in ${config.getTestingDir()}/${it.name}!")
        run(it)
      }
    }
  }

  override fun runTuning() {
    if ("tokenizer" in config.getPreprocessors()) {
      files.forEach {
        println("${this::class.simpleName}-> I'm running in ${config.getTuningDir()}/${it.name}!")
        run(it)
      }
    }
  }
}
