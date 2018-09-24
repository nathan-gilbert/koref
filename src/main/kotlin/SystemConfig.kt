package koref

class SystemConfig(private val settingsFile:String = "settings.yml") {
    var isInitialized:Boolean = false

    init {
        if (!settingsFile.isEmpty()) {
            //todo read in the file and set configuration values...
            isInitialized = true
        }
    }
}
