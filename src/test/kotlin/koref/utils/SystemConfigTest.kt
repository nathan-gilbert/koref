package koref.utils

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class SystemConfigTest {
  companion object {
    private const val testSettingsStr = """baseDataDir: /Users/nathan/Documents/Data/coreference/muc6
workingDir: /Users/nathan/Projects/koref
trainDataDir: muc6-train
trainFileList: muc6.train.filelist
testDataDir: muc6-test
testFileList: muc6.test.filelist
preprocessors:
  - tokenizer
"""
  }

  @Test
  fun `SystemConfig handles not finding settings file`() {
    val config = SystemConfig(settingsFile = "myFile.yml")
    assertFalse(config.isInitialized)
  }

  @Test
  fun `SystemConfig handles incorrect yml file`() {
    assertThrows<MissingKotlinParameterException> {
      SystemConfig(settingsFile = "detekt.yml")
    }
  }

  @Test
  fun `SystemConfig opens the default settings file`(@TempDir tempDir: Path) {
    val tempSettingsFile = File(tempDir.toString(), "test-settings.yml")
    tempSettingsFile.writeText(testSettingsStr)

    val config = SystemConfig(tempSettingsFile.absolutePath)
    assertTrue(config.isInitialized)
  }

  @Test
  fun `SystemConfigDto contains correct directories`(@TempDir tempDir: Path) {
    val tempSettingsFile = File(tempDir.toString(), "test-settings.yml")
    tempSettingsFile.writeText(testSettingsStr)
    val dataDirCount = 4
    val config = SystemConfig(tempSettingsFile.absolutePath)
    assertThat(config.getDataDirs().keys.size).isEqualTo(dataDirCount)
    config.setTuneDataDir("some-tuning-dir")
    assertThat(config.getDataDirs()["tune"]).isEqualTo("some-tuning-dir")
    assertThat(config.getTuningDir()).isEqualTo("some-tuning-dir")
    assertThat(config.getTrainingDir()).isEqualTo("muc6-train")
    assertThat(config.getTestingDir()).isEqualTo("muc6-test")
  }

  @Test
  fun `SystemConfig has working directory set`(@TempDir tempDir: Path) {
    val tempSettingsFile = File(tempDir.toString(), "test-settings.yml")
    tempSettingsFile.writeText(testSettingsStr)
    val config = SystemConfig(tempSettingsFile.absolutePath)
    assertThat(config.getWorkingDir()).isEqualTo("/Users/nathan/Projects/koref")
  }

  @Test
  fun `SystemConfig handles file lists`(@TempDir tempDir: Path) {
    val tempSettingsFile = File(tempDir.toString(), "test-settings.yml")
    tempSettingsFile.writeText(testSettingsStr)
    val config = SystemConfig(tempSettingsFile.absolutePath)
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

  @Test
  fun `SystemConfig handles preprocessor list`(@TempDir tempDir: Path) {
    val tempSettingsFile = File(tempDir.toString(), "test-settings.yml")
    tempSettingsFile.writeText(testSettingsStr)
    val config = SystemConfig(tempSettingsFile.absolutePath)
    assertThat(config.getPreprocessors().size).isEqualTo(1)
  }
}
