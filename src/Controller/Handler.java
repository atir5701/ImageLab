package Controller;

import java.io.IOException;

/**
 * The interface represents the various
 */

public interface Handler {

  void readCommand(String[] command) throws IOException;
}
