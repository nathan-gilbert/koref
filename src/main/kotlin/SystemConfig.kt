package koref

import koref.utils.YamlParser

data class SystemConfigDto(val baseDataDir: String)

<<<<<<< HEAD
class SystemConfig(private val settingsFile:String = "settings.yml") {
  var isInitialized:Boolean = false

  init {
    if (!settingsFile.isEmpty()) {
      //todo read in the file and set configuration values...
      val config: SystemConfigDto = YAMLParser.parseDto("settings.yml", SystemConfigDto::class)
=======
class SystemConfig(private val settingsFile: String = "settings.yml") {
  var isInitialized: Boolean = false

  init {
    if (!settingsFile.isEmpty()) {

      //todo read in the file and set configuration values...
      val config: SystemConfigDto = YamlParser.parseDto("settings.yml", SystemConfigDto::class)
>>>>>>> 37943631c79107447e665583b5ed317b12579897
      isInitialized = true
    }
  }
}
