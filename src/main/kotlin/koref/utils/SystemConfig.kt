package koref.utils

import java.io.File

data class SystemConfigDto(
    val workingDir: String,
    val baseDataDir: String,
    val trainDataDir: String,
    val trainFileList: String,
    val testDataDir: String,
    val testFileList: String,
    var tuneDataDir: String?,
    var tuneFileList: String?
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

  fun setTuneDataDir(dir: String) {
    config.tuneDataDir = dir
  }

  fun setTuneFileList(fileList: String) {
    config.tuneFileList = fileList
  }

  fun getDataDirs(): Map<String, String> {
    val dataDirs = mutableMapOf<String, String>()
    dataDirs["base"] = config.baseDataDir
    dataDirs["train"] = config.trainDataDir
    dataDirs["test"] = config.testDataDir
    dataDirs["tune"] = config.tuneDataDir ?: ""
    return dataDirs
  }

  fun getTrainingFiles(): List<String> {
    val inFile = "${config.baseDataDir}/${config.trainDataDir}/${config.trainFileList}"
    return readFileAsLines(inFile)
  }

  fun getTestingFiles(): List<String> {
    val inFile = "${config.baseDataDir}/${config.testDataDir}/${config.testFileList}"
    return readFileAsLines(inFile)
  }

  fun getTuningFiles(): List<String> {
    if (config.tuneFileList != null) {
      val inFile = "${config.baseDataDir}/${config.tuneDataDir}/${config.tuneFileList}"
      return readFileAsLines(inFile)
    }
    return emptyList()
  }

  fun getWorkingDir(): String {
    return config.workingDir
  }

  private fun readFileAsLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}
