package koref

/**
 * The main driver for the koref backend.
 *
 * @constructor Creates a server on port 8099
 */
fun main(args : Array<String>) {
    val config = SystemConfig()

    println("This is Koref.")
    println("Am I configured: " + if (config.isInitialized) "Yes" else "No")
}
