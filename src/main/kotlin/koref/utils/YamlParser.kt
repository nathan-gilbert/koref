package koref.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.FileSystems
import java.nio.file.Files
import kotlin.reflect.KClass

object YamlParser {
  private val mapper = let {
    val mapper = ObjectMapper(YAMLFactory())
    mapper.registerModule(
      KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, false)
        .configure(KotlinFeature.NullToEmptyMap, false)
        .configure(KotlinFeature.NullIsSameAsDefault, false)
        .configure(KotlinFeature.SingletonSupport, false)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()
    )
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
    mapper
  }

  fun <T : Any> parseDto(fileName: String, dto: KClass<T>): T {
    return Files.newBufferedReader(
      FileSystems
        .getDefault()
        .getPath(fileName)
    ).use { mapper.readValue(it, dto.java) }
  }

  fun <T : Any> parseDtoFromString(parseThis: String, dto: KClass<T>): T {
    return mapper.readValue(parseThis, dto.java)
  }
}
