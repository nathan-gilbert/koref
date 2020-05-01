package koref.data

import koref.preprocessors.tokenizers.SimpleWhiteSpaceTest
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

internal class RawTextDocumentTest {
  companion object {
    private const val text = "This is my test document."
    private val resourcesDirectory = File("src/test/resources").absolutePath
    private val sampleFile = "$resourcesDirectory/sample.txt"
    private const val sampleText = "This is a test sample."
  }

  @Test
  fun `get document name`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.name).isEqualTo("myTest")
  }

  @Test
  fun `get document text when null`() {
    val rawText = RawTextDocument("myTest")
    assertThat(rawText.getText()).isEqualTo("")
  }

  @Test
  fun `get raw document text`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.getText()).isEqualTo(text)
  }

  @Test
  fun `get annotations`() {
    val rawText = RawTextDocument("myTest", text)
    assertThat(rawText.annotations.keys.size).isEqualTo(0)
  }

  @Test
  fun `read from file`() {
    val rawText = RawTextDocument("myTest")
    rawText.readRawTextFile(sampleFile)
    assertThat(rawText.getText()).contains(sampleText)
  }

  @Test
  fun `read from no file`() {
    val rawText = RawTextDocument("myTest")
    assertThrows<FileNotFoundException> {
      rawText.readRawTextFile("noFile")
    }
  }

  @Test
  fun `read empty file`(@TempDir tempDir: Path) {
    val tempFile = File(tempDir.toString(), "tmp.txt")
    tempFile.writeText("")
    val rawText = RawTextDocument("myTest")
    rawText.readRawTextFile(tempFile.absolutePath)
    assertThat(rawText.getText()).contains("")
  }
}