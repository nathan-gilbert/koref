package koref.preprocessors

import koref.utils.SystemConfig
import java.io.File
import java.nio.file.Path

enum class PreprocessorType {
  UNKNOWN, TOKENIZER
}

/**
 * TODO
 *
 */
abstract class Preprocessor(protected val config: SystemConfig) {
  abstract val annotationName: String
  val annotations = ArrayList<Annotation>()

  /**
   * Run the preprocessor
   */
  abstract fun run()

  /**
   * Write the annotation set to file
   *
   * @param outDir - directory to write file
   */
  fun writeAnnotationsToFile(outDir: Path) {
    File("${outDir.toString()}/$annotationName").bufferedWriter().use {
      out -> annotations.forEach { out.write(it.toString()) }
    }
  }
}
