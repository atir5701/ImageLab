package Controller;

import Controller.Commands.AbstractCommandExecuter;
import Controller.Commands.Blur;
import Controller.Commands.Brighten;
import Controller.Commands.BrightnessComponent;
import Controller.Commands.ColorComponent;
import Controller.Commands.HorizontalFlip;
import Controller.Commands.Load;
import Controller.Commands.RGBCombine;
import Controller.Commands.RGBSplit;
import Controller.Commands.Save;
import Controller.Commands.Sepia;
import Controller.Commands.Sharpen;
import Controller.Commands.VerticalFlip;
import Model.ImageOperations;
import Model.Operations;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;


public class CommandHandler implements Handler {
  Operations operations;
  Map<String, BiFunction<String[],Integer,AbstractCommandExecuter>> commandMap;

  public CommandHandler() {
    this.operations = new ImageOperations();
    commandMap = new HashMap<>();
    commandMap.put("load",(cmd,a)-> new Load(cmd,3));
    commandMap.put("save",(cmd,a)-> new Save(cmd,3));
    commandMap.put("brighten",(cmd,a)-> new Brighten(cmd,4));
    commandMap.put("horizontal-flip",(cmd,a)-> new HorizontalFlip(cmd,3));
    commandMap.put("vertical-flip",(cmd,a)-> new VerticalFlip(cmd,3));
    commandMap.put("blur",(cmd,a)-> new Blur(cmd,3));
    commandMap.put("sharpen",(cmd,a)-> new Sharpen(cmd,3));
    commandMap.put("sepia",(cmd,a)-> new Sepia(cmd,3));
    commandMap.put("red-component",(cmd,a)->new ColorComponent(cmd,3));
    commandMap.put("green-component",(cmd,a)->new ColorComponent(cmd,3));
    commandMap.put("blue-component",(cmd,a)->new ColorComponent(cmd,3));
    commandMap.put("value-component",(cmd,a)->new BrightnessComponent(cmd,3));
    commandMap.put("luma-component",(cmd,a)->new BrightnessComponent(cmd,3));
    commandMap.put("intensity-component",(cmd,a)->new BrightnessComponent(cmd,3));
    commandMap.put("rgb-split",(cmd,a)->new RGBSplit(cmd,5));
    commandMap.put("rgb-combine",(cmd,a)->new RGBCombine(cmd,5));
  }

  @Override
  public void readCommand(String[] input) throws IllegalArgumentException {
    String result = String.join(" ", input);
    System.out.println(result);
    String command = input[0];
    BiFunction<String[],Integer,AbstractCommandExecuter> cmd = this.commandMap.get(command);
    if(cmd == null) {
      throw new IllegalArgumentException("Unknown command: ");
    }
    AbstractCommandExecuter ex = cmd.apply(input,0);
    ex.execute(operations);
  }
}
