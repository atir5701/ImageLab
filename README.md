## Overview

This Java-based image processing application is designed using the Model-View-Controller (MVC) design pattern. It empowers users to perform a variety of image manipulation operations, including:

- **Getting individual channel**
- **Gettiing various brightness components**
- **Brightening**
- **Darkening**
- **Blurring**
- **Sepia transformation**
- **Sharpening**
- **Flipping**
- **Splitting RGB components**
- **Combining RGB components**
  
### User Input and Command Script

Users interact with the application by providing a script file that specifies a sequence of commands also user can provide the command in a single line through the command prompt. Each command must detail:

1. The operation to be performed on the image.
2. The path to the image file (supported formats: JPEG, PNG, and PPM (P-3)).
3. The name for the new image that will be generated as a result of the operation.

If the command provided is invalid or not found a suitbale message would be provided to the user. The program only terminates when the user provides an quit command.

The structure of various commands is as follow:

1. **load image-path image-name**: Load an image from the specified path and refer it to henceforth in the program by the given image name.
2. **save image-path image-name**: Save the image with the given name to the specified path which should include the name of the file.
3. **red-component image-name dest-image-name**: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name. Similar commands for green, blue, value, luma, intensity components should be supported. Note that the images for value, luma and intensity will be greyscale images.
4. **horizontal-flip image-name dest-image-name**: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
5. **vertical-flip image-name dest-image-name**: Flip an image vertically to create a new image, referred to henceforth by the given destination name.
6. **brighten increment image-name dest-image-name**: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).
7. **rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue**: split the given image into three images containing its red, green and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component and blue-component commands.
8. **rgb-combine image-name red-image green-image blue-image**: Combine the three images that are individually red, green and blue into a single image that gets its red, green and blue components from the three images respectively.
9. **blur image-name dest-image-name**: blur the given image and store the result in another image with the given name.
10. **sharpen image-name dest-image-name**: sharpen the given image and store the result in another image with the given name.
11. **sepia image-name dest-image-name**: produce a sepia-toned version of the given image and store the result in another image with the given name.
12.  **quit** : command by which the user can end the application.


The application also allows users to specify the directory where the processed images will be saved.

Command to run the script path with all the above-mentioned commands is:

**run script-file**: Load and run the script commands in the specified file.

Whenever the user provides the load, run or save command, the user must specify the absolute file path from where the image is to be read or stored or from where the script file is present. If the path provided 
is not a valid one then an error is thrown.

###  Run Application
The user can run this application, either via the command line or by providing a script file and then running that script file using "run" command, following are the steps user can perform either of those.
The user would have to make sure of the following before running the application:-
1. They have installed Java Development Kit installed on their system. User can check this using the following command on their local terminal/command prompt.
```bash
java -version
```
2. The user needs to navigate to the folder on his/her local system where the ImageProcessingApplication.java file is located.
```bash
cd path/to/directory
```
3. The user needs to compile the java file using the follwoing command.
```bash
javac src/controller/*.java src/model/*.java src/*.java
```

4. Now, the user needs to run this java file using the following command.
```bash
java -cp src ImageProcessingApplication
```

Now, the user can follow the steps shown as below.

#### Command Line Interface

1. Load an image:
```
load path/to/image.jpg input-image
```
Example: 
```
load images/manhattan-small.png man
```

2. Apply operations as needed.
Example:
```
blur man man-edited
```

3. Save the processed image:
```
save path/to/output.png output-image
```
Example: 
```
save res/man-edited.png man-edited
```

4. To terminate the program provide "quit":
```
quit
 ```
#### Script Execution

1. Create a text file containing commands (e.g., `script.txt`)

2. Run `src/ImageProcessingApplication.java` file. The program will start and wait for user input.

3. Execute the script:
```bash
run script.txt
```
(NOTE: provide the absolute path of where script.txt is located in your system.)
Example: 
```bash
run D:/PDP/Assignment4/scripts/script.txt
```

### Image Format Conversion

In addition to standard image manipulation, the application supports interconversion between different image formats, enabling users to easily convert images from one format to another.

### Multi-Image Processing

The application can handle multiple images simultaneously, allowing users to load and process various images in one session.

## Project Structure

- **model/**: Contains classes representing the data and logic of the application.
  - `ImageModel.java`: Manages image metadata and pixel data.
  - `Operations.java`: Interface which defined the various operations to be performed.
  - `ImageOperations.java`: Class that implements the Operations interface. Contains methods for various image processing operations.

- **controller/**: Contains the main logic files for handling commands and scripting for the package, as well as the classes for each and every commands.
  - `ImageAppController` : Interface which defines the start operation, which needs to be carried out by Controller.
  - `CommandReader.java` : Reads and interprets script files  also manages the single line command provided for command execution and implements the ImageAppController. This has a startApplication function which basically starts the application. Also this parse the input
commands and gives the filter command to the CommandHandler class
  - `CommandHandler.java` : This class has a takes the parsed commands from CommandReader, and using the map redirect the command to the appropraite class design to handle the specified command.
  - `CommandExecuter.java`: Interface which defines the method need to be implemented by each command.
  - `AbstractCommandExecuter.java` : Abstract class to implement the command method between all command classes. All command Classes in the package extends this class. Contains
  implementation of common method of various command classes. This class implement the CommandExecuter interface.
  - `Blur`: Redirects the controller to the blur implementation in Model. Also checks if the command provided is correct or not. If not throws error.
  - `Brighten`: Redirects the controller to the brighten implementation in Model. Also checks if the command provided is correct or not. If not throws error.
  - `BirghtnessComponent`: Redirect the controller to the getBrightnessComponent in Model. Also
    passes the appropriate handler based on input. The handler can be value, luma, or intensity.
  -` ColorComponent.java`: Redirect the controller to the getColorComponent in Model. Also
    passes the appropriate color based on input. The handler can be 0,1 or 2 which are
    red, green or blue.
  - `HorizontalFlip.java`: Redirect the controller to the horizontalFlip implementation in Model. Check if the command is correct or not.
  -  `VerticalFlip.java` : Redirect the controller to the horizontalFlip implementation in Model. Check if the command is correct or not.
  - `Sepia.java` : Redirect the controller to the sepia implementation in Model. Check if the command is correct or not.
  -  `Sharpen.java` : Redirect the controller to the sharpen implementation in the Model. Check if the command is correct or not.
  -  `RBGCombine` : Redirect the controller to the rgbCombine implementation in Model. Check if the command is correct or not.
  -  `RBGSplit` : Redirect the controller to the rgbSplit implementation in Model. Check if the command is correct or not.
  - `Save.java`: The class obtains the matrix representation of image from Model. On the basis of the extension of the image which is to be saves the image gets saved at suitable filepath.
  - `load.java `: The class converts any input format to a three dimension matrix to be passed to the Model.
      

- **ImageProcessingApplication**: The entry point of the application. This is the main class where the controller and model objects are instantiated, and from here the controller starts to take the user input.

![Folder Structure](FolderStructure.png)

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.khoury.northeastern.edu/aryanshah295/Assignment-4/
   ```
2. Navigate to the project directory:
   ```bash
   cd repository
   ```

## Class Description 
The application is based on MVC archtecture
- **model** :  This package contains all the classes which are needed to manipulate and store metadata associated with the each image.
    1. `Operations.java` : This interface represents the contract which defines the operations that can be performed on images, regardless of their specific types. The                     available methods include: retrieving components, flipping, brightening, splitting, combining, blurring, sharpening, and applying sepia filtering.

    2. `ImageOperations.java` : The ImageOperations class implements the Operations interface, providing implementations for various image manipulation and processing tasks. This class also maintains a map that stores instances of all images loaded and processed during the execution of the application, allowing efficient access and manipulation throughout the script's runtime.

  3. `ImageModel.java` : This class represents an image, so the class contains all the variables and method needed to handle an image. An image is a 3-d matrix of pixels.

- **controller** : This package contains all the classes which are needed to have a communication between model and view and is responsible for the I/O operations for the application.
  1. `CommandReader.java` : The CommandReader class is responsible for reading and executing commands from a script file as well as the ones provided in command line. It utilizes a CommandHandler to  process commands parsed from the file/command-line. This class provides methods to read commands from a specified file path, ensuring that the file is in the correct format and skips comments.

  2. `ImageAppController.java` : This is an interface which defines a method for processing commands. Implementing classes should provide the logic to handle the command input       into the system.

  3. `CommandHandler.java` : The CommandHandler class is responsible for managing various commands related to image processing. It utilizes a HashMap to associate command strings with their respective command classes, allowing for the dynamic creation of command objects using BiFunction.

  4. `CommandExecuter.java`: Interface which defines the method ( execute() ) to be implemented by each command. Other classes such as Load, Save, Brighten etc in the package implement this interface.
   
  5. `AbstractCommandExecuter.java` : Abstract class to implement the common methods between all command classes. All Classes in the package extends this class. 

## Class Diagram
Below is the class diagram of the application generated using the Intellije-IDE.

![Class Diagram](ClassDiagram.png)
       
## Citations of the images used for testing.

 1. [`Bird.png`](https://www.dpreview.com/samples/3925134721/fujifilm-x-a3-sample-gallery)

 2. [`Koala.ppm`](https://animals-are-cool.fandom.com/wiki/Koala)

 3. [`Panda.jpeg`](https://www.freepik.com/photos/simple-watercolor-panda/2)
