package org.project.View;

import java.io.OutputStream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * An {@code OutputStream} implementation that redirects text output to a JavaFX {@code TextArea}.
 *
 * <p>This class is useful for capturing standard output or logging and displaying it in a
 * GUI text area. It ensures thread-safe updates to the JavaFX UI using {@code Platform.runLater}.
 */
public class TextAreaOutputStream extends OutputStream {

  private final TextArea textArea;

  /**
   * Constructs a {@code TextAreaOutputStream} for the specified {@code TextArea}.
   *
   * @param textArea the JavaFX {@code TextArea} to which output will be redirected
   */
  public TextAreaOutputStream(TextArea textArea) {
    this.textArea = textArea;
  }

  /**
   * Writes a single byte to the text area.
   *
   * <p>This method converts the byte to a character and appends it to the text area.
   * The update is performed on the JavaFX Application Thread using {@code Platform.runLater}.
   *
   * @param b the byte to be written
   */
  @Override
  public void write(int b) {
    Platform.runLater(() -> textArea.appendText(String.valueOf((char) b)));
  }

  /**
   * Writes a portion of a byte array to the text area.
   *
   * <p>This method converts the specified portion of the byte array to a string and appends it
   * to the text area. The update is performed on the JavaFX Application Thread using
   * {@code Platform.runLater}.
   *
   * @param b   the byte array containing the data to be written
   * @param off the start offset in the array
   * @param len the number of bytes to write
   */
  @Override
  public void write(byte[] b, int off, int len) {
    Platform.runLater(() -> textArea.appendText(new String(b, off, len)));
  }
}
