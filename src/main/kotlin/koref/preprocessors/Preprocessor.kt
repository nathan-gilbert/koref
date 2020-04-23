package koref.preprocessors

enum class PreprocessorType {
  UNKNOWN, TOKENIZER
}

/**
 * TODO
 *
 */
interface Preprocessor {
  /**
   * Run the preprocessor
   */
  fun run()

  /**
   *
   */
  fun writeAnnotationsToFile()
}
