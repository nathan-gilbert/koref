package koref

import koref.preprocessors.Tokenizer
import koref.utils.SystemConfig

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
fun preprocess(config: SystemConfig) {
  println("Preprocessors to run: ${config.getPreprocessors()}")
  if ("preprocessor" in config.getPreprocessors()) {
    val tokenizer = Tokenizer()
    tokenizer.run()
  }
}

/**
 * The main driver for the koref backend.
 */
fun main(args: Array<String>) {
  val opts = getOpts(args)
  val settingsFile = opts.getOrDefault("-s", listOf()).firstOrNull()
  val config = SystemConfig(settingsFile)

  println("This is the Koref System")
  println("Am I configured: " + if (config.isInitialized) "Yes" else "No")

}
