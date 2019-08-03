package koref

import koref.utils.SystemConfig

/**
 * The main driver for the koref backend.
 */
fun main(args: Array<String>) {
  val config = SystemConfig()

  println("This is Koref.")
  println("Am I configured: " + if (config.isInitialized) "Yes" else "No")
}
