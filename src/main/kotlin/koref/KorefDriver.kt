package koref

import koref.data.Corpus
import koref.preprocessors.tokenizers.SimpleWhiteSpace
import koref.utils.SystemConfig
import java.io.File

fun getOpts(args: Array<String>): Map<String, List<String>> {
  var last = ""
  return args.fold(mutableMapOf()) {
    acc: MutableMap<String, MutableList<String>>, s: String ->
    acc.apply {
      if (s.startsWith('-')) {
        this[s] = mutableListOf()
        last = s
      } else this[last]?.add(s)
    }
  }
}

/**
 * Run the selected preprocessors over the selected data sets.
 */
fun preprocess(corpus: Corpus, config: SystemConfig) {
  println("Preprocessors to run: ${config.getPreprocessors()}")
  if ("tokenizer" in config.getPreprocessors()) {
    val tokenizer = SimpleWhiteSpace("tokens", config, corpus.docs)
    tokenizer.runTrain()
  }
}

fun getCorpus(config: SystemConfig): Corpus {
  val baseDir = "${config.getBaseDataDir()}${File.separator}${config.getTrainingDir()}"
  val corpus = Corpus("training", baseDir, config.getTrainingFiles())
  corpus.createDocuments()
  return corpus
}

/**
 * The main driver for the koref backend.
 */
fun main(args: Array<String>) {
  val opts = getOpts(args)
  val settingsFile = opts.getOrDefault("-s", listOf()).firstOrNull() ?: "settings.yml"
  val config = SystemConfig(settingsFile)

  println("This is the Koref System")
  println("Am I configured: " + if (config.isInitialized) "Yes" else "No")

  if (config.isInitialized) {
    val corpus = getCorpus(config)
    if(config.getPreprocessors().size > 0) preprocess(corpus, config)
  }
}
