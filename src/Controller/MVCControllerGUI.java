package controller;

/**
 * The MVCControllerGUI interface defines
 * the contract for controllers that
 * are responsible for starting and initializing a
 * GUI-based application.
 * Implementations of this interface should
 * define how to set up the view
 * and model interaction for the application.
 */

public interface MVCControllerGUI {

  /**
   * Starts the GUI-based application by
   * adding the feature to the view.
   * This method sets up the necessary
   * components for the user interface
   * to interact with the application.
   */
  void startGUIBasedApplication();
}
