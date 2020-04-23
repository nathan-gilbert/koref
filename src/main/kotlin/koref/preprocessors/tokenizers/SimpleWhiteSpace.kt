package koref.preprocessors.tokenizers

class SimpleWhiteSpace : Tokenizer {
  override fun run() {
    println("${SimpleWhiteSpace::class.simpleName}-> I'm running!!")
  }

  override fun writeAnnotationsToFile() {
    TODO("Not yet implemented")
  }
}
