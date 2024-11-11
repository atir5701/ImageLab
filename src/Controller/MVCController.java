package controller;


import model.OperationsV2;
import view.GuiView;
import view.IView;

public class MVCController {

  public void go(OperationsV2 model, IView view){
    Features f = new ImageApplicationFeatures(model, view);
    view.addFeature(f);
  }
}
