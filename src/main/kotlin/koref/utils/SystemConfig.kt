package koref.utils

import java.io.File

data class SystemConfigDto(
  val baseDataDir: String,
  val trainingDataDir: String,
  val testDataDir: String,
  var tuningDataDir: String?
)

class SystemConfig(settingsFile: String?) {
  private lateinit var config: SystemConfigDto
  var isInitialized: Boolean = false
    private set

  init {
    // check that option was passed in
    var sFile = settingsFile
    if (sFile == null) {
      sFile = "settings.yml"
    }

    // check if file actually exists
    val file = File(sFile)
    val fileExists = file.exists()
    if (fileExists) {
      config = YamlParser.parseDto(sFile, SystemConfigDto::class)
      isInitialized = true
    }
  }

  fun setTuningDir(dir: String) {
    config.tuningDataDir = dir
  }

  fun getDataDirs(): Map<String, String> {
    val dataDirs = mutableMapOf<String, String>()
    dataDirs["base"] = config.baseDataDir
    dataDirs["training"] = config.trainingDataDir
    dataDirs["testing"] = config.testDataDir
    dataDirs["tuning"] = config.tuningDataDir ?: ""

    return dataDirs
  }
}
