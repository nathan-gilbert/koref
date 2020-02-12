package koref.utils

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SystemConfigTest {
  companion object;

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
    val dataDirCount = 4
    val config = SystemConfig(null)
    assertThat(config.getDataDirs().keys.size).isEqualTo(dataDirCount)
    config.setTuneDataDir("some-tuning-dir")
    assertThat(config.getDataDirs()["tune"]).isEqualTo("some-tuning-dir")
  }

  @Test
  fun `SystemConfig has working directory set`() {
    val config = SystemConfig(null)
    assertThat(config.getWorkingDir()).isNotNull()
  }

  @Test
  fun `SystemConfig handles file lists`() {
    val config = SystemConfig(null)
    val testFiles = 30
    assertThat(config.getTestingFiles().size).isEqualTo(testFiles)
    val trainFiles = 30
    assertThat(config.getTrainingFiles().size).isEqualTo(trainFiles)
    var tuneFiles = 0
    assertThat(config.getTuningFiles().size).isEqualTo(tuneFiles)
    config.setTuneDataDir(config.getDataDirs()["train"] ?: "")
    config.setTuneFileList("muc6.train.filelist")
    tuneFiles = 30
    assertThat(config.getTuningFiles().size).isEqualTo(tuneFiles)
  }
}
