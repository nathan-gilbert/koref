package koref.utils

data class SystemConfigDto(
  val baseDataDir: String
)

class SystemConfig(settingsFile: String = "settings.yml") {
  private lateinit var config: SystemConfigDto
  var isInitialized: Boolean = false

  init {
    if (settingsFile.isNotEmpty()) {
      //todo read in the file and set configuration values...
      config = YamlParser.parseDto("settings.yml", SystemConfigDto::class)
      isInitialized = true
    }
  }
}
