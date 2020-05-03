package koref.preprocessors

import koref.utils.SystemConfig
import koref.data.Annotation
import koref.data.Document
import java.io.File

enum class PreprocessorType {
  UNKNOWN, TOKENIZER
}

/**
 * Preprocessor
 * @property config
 * @property files
 */
abstract class Preprocessor(protected val config: SystemConfig, protected val files: ArrayList<Document>) {
  abstract val annotationName: String
  val annotations = ArrayList<Annotation>()
  private val annotationDir = "annotations"

  /**
   * Run the preprocessor on the training documents
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
   * @param doc
   * @param text
   */
  abstract fun getStartOffset(doc: Document, text: String): Int

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
    File("$outDir/$annotationDir/${this.annotationName}").bufferedWriter().use {
      out -> annotations.forEach { out.write(it.toString()) }
    }
  }
}
