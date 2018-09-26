package koref

import koref.utils.YAMLParser

data class SystemConfigDto(val baseDataDir: String)

class SystemConfig(private val settingsFile:String = "settings.yml") {
    var isInitialized:Boolean = false

    init {
        if (!settingsFile.isEmpty()) {
            //todo read in the file and set configuration values...
            val config: SystemConfigDto = YAMLParser.parseDto("settings.yml", SystemConfigDto::class)
            isInitialized = true
        }
    }
}
