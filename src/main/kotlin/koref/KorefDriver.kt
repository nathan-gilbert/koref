package koref

import koref.utils.SystemConfig

fun getOpts(args: Array<String>): Map<String, List<String>>
{
  var last = ""
  return args.fold(mutableMapOf()) {
    acc: MutableMap<String, MutableList<String>>, s: String ->
    acc.apply {
      if (s.startsWith('-')) {
        this[s] = mutableListOf()
        last = s
      } else this[last]?.add(s)
    }
  }
}

/**
 * The main driver for the koref backend.
 */
fun main(args: Array<String>) {
  val opts = getOpts(args)
  val settingsFile = opts.getOrDefault("-s", listOf()).firstOrNull()
  val config = SystemConfig(settingsFile)

  println("This is Koref.")
  println("Am I configured: " + if (config.isInitialized) "Yes" else "No")
}
