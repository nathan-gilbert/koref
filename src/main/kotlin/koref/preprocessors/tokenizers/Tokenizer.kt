package koref.preprocessors.tokenizers

import koref.data.Document
import koref.preprocessors.Preprocessor
import koref.utils.SystemConfig

abstract class Tokenizer(config: SystemConfig, files: ArrayList<Document>) : Preprocessor(config, files)
