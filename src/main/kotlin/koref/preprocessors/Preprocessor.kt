package koref.preprocessors

import koref.utils.SystemConfig
import koref.data.Annotation
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
  val annotations = ArrayList<Annotation>()

  /**
   * Run the preprocessor
   */
  abstract fun runTrain()

  /**
   *
   */
  abstract fun runTest()

  /**
   *
   */
  abstract fun runTuning()

  /**
   * Add an annotation to this preprocessor
   *
   * @param ann -- annotation to add
   */
  fun addAnnotation(ann: Annotation) {
    annotations.add(ann)
  }

  /**
   * Write the annotation set to file
   *
   * @param outDir - directory to write file
   */
  fun writeAnnotationsToFile(outDir: String) {
    File("$outDir/${this.annotationName}").bufferedWriter().use {
      out -> annotations.forEach { out.write(it.toString()) }
    }
  }
}
