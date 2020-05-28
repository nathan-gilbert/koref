package koref.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

internal class RawTextDocumentTest {
  companion object {
    private const val text = "This is my test document."
    private const val sampleText = "This is a test sample."
    private const val baseDir = "/Users/nathan/Documents/Data/raw/example"
  }

  @Test
  fun `get document name`() {
    val rawText = RawTextDocument("myTest", baseDir, text)
    assertThat(rawText.name).isEqualTo("myTest")
  }

  @Test
  fun `get raw document text`() {
    val rawText = RawTextDocument("myTest", baseDir, text)
    assertThat(rawText.getText()).isEqualTo(text)
  }

  @Test
  fun `get annotations`() {
    val rawText = RawTextDocument("myTest", baseDir, text)
    assertThat(rawText.annotations.keys.size).isEqualTo(0)
  }

  @Test
  fun `read from file`(@TempDir tempDir: Path) {
    val tempFile = File(tempDir.toString(), "raw.txt")
    tempFile.writeText(sampleText)
    val rawText = RawTextDocument(".", tempDir.toAbsolutePath().toString())
    assertThat(rawText.getText()).contains(sampleText)
  }

  @Test
  fun `read from no file`() {
    assertThrows<FileNotFoundException> {
      RawTextDocument("/some/dir","myTest")
    }
  }

}