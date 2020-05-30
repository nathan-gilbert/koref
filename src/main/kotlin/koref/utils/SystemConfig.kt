package koref.utils

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import java.io.File

data class SystemConfigDto(
    val workingDir: String,
    val baseDataDir: String,
    val trainDataDir: String,
    val trainFileList: String,
    val testDataDir: String,
    val testFileList: String,
    var tuneDataDir: String?,
    var tuneFileList: String?,
    val preprocessors: ArrayList<String>?,
    val modelDirectory: String?
)

@Suppress("TooManyFunctions", "EmptyCatchBlock")
class SystemConfig(settingsFile: String) {
  private lateinit var config: SystemConfigDto
  var isInitialized: Boolean = false
    private set

  init {
    // check if file actually exists
    val file = File(settingsFile)
    val fileExists = file.exists()
    if (fileExists) {
      config = YamlParser.parseDto(settingsFile, SystemConfigDto::class)
      isInitialized = true
    } else {
      try {
        // try to parse the string that was passed in
        config = YamlParser.parseDtoFromString(settingsFile, SystemConfigDto::class)
        isInitialized = true
      } catch (e: MismatchedInputException) { /* TODO add logging here */ }
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

  fun getBaseDataDir(): String = config.baseDataDir
  fun getTrainingDir(): String = config.trainDataDir
  fun getTestingDir(): String = config.testDataDir
  fun getTuningDir(): String? = config.tuneDataDir

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

  fun getWorkingDir(): String = config.workingDir
  fun getPreprocessors(): ArrayList<String> = config.preprocessors ?: arrayListOf()
  fun getModelDirectory(): String = config.modelDirectory ?: ""

  private fun readFileAsLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }
}
