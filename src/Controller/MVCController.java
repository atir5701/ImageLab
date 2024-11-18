package controller;


import model.OperationsV3;
import view.IView;

/**
 * The MVCController class is responsible for initializing the features
 * in the Model-View-Controller (MVC) architecture by linking the model
 * and view together. It creates an instance of the features,
 * adds them to the view, and establishes the interaction between
 * the model and the view.
 */

public class MVCController implements MVCControllerGUI {
  private final IView view;
  private final Features f;


  /**
   * Constructs an MVCController that links the specified model and view,
   * and initializes the features that handle the interaction between them.
   *
   * @param model the model that performs the logical operations
   * @param view  the view that displays the user interface
   */
  public MVCController(OperationsV3 model, IView view) {
    this.view = view;
    this.f = new ImageApplicationFeatures(model, this.view);
  }

  /**
   * Starts the GUI-based application by adding the feature to the view.
   * This method sets up the necessary components for the user interface
   * to interact with the application.
   */
  @Override
  public void startGUIBasedApplication() {
    this.view.addFeature(f);
  }
}
