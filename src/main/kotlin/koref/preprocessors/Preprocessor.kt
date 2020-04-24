package koref.preprocessors

import koref.utils.SystemConfig
import java.io.File

enum class PreprocessorType {
  UNKNOWN, TOKENIZER
}

/**
 * TODO
 *
 */
abstract class Preprocessor(protected val config: SystemConfig) {
  abstract val annotationName: String
  protected val annotations = ArrayList<Annotation>()

  /**
   * Run the preprocessor
   */
  abstract fun run()

  /**
   * Write the annotation set to file
   *
   * @param outFile - fully qualified filename for output
   */
  fun writeAnnotationsToFile(outFile: String) {
    File(outFile).bufferedWriter().use {
      out -> annotations.forEach { out.write(it.toString()) }
    }
  }
}
