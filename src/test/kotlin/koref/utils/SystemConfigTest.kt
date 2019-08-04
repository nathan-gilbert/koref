package koref.utils

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SystemConfigTest {
  companion object {
    const val dataDirCount = 4
  }

  @Test
  fun `SystemConfig handles not finding settings file`() {
    val config = SystemConfig(settingsFile = "myfile.yml")
    assertFalse(config.isInitialized)
  }

  @Test
  fun `SystemConfig handles incorrect yml file`() {
    assertThrows<MissingKotlinParameterException> {
      SystemConfig(settingsFile = "detekt.yml")
    }
  }

  @Test
  fun `SystemConfig opens the default settings file`() {
    val config = SystemConfig(null)
    assertTrue(config.isInitialized)
  }

  @Test
  fun `SystemConfigDto contains correct directories`() {
    val config = SystemConfig(null)
    assertThat(config.getDataDirs().keys.size).isEqualTo(dataDirCount)
    config.setTuningDir("some-tuning-dir")
    assertThat(config.getDataDirs()["tuning"]).isEqualTo("some-tuning-dir")
  }
}
