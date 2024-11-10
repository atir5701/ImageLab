Image Processing Application
===


## Overview


This Java-based image processing application is designed using the Model-View-Controller (MVC) design pattern.
The system implements various design patterns, enabling adaptable and dynamic processing of user commands.
It empowers users to perform a variety of image manipulation operations, including:



- **Getting individual channel**
- **Getting various brightness components**
- **Brightening**
- **Darkening**
- **Blurring**
- **Sepia transformation**
- **Sharpening**
- **Flipping**
- **Splitting RGB components**
- **Combining RGB components**

### New Features :
- **Compressing**
- **Histogram**
- **Color Correction**
- **Levels Adjustment**
- **Split Preview**

## Key Components

- **Controller**: The main component responsible for interpreting user input and directing commands as needed.
- **Model**: Handles the execution of image-related operations.
- **Commands**: Each command performs a specific image manipulation task, such as brightening, blurring, saving, or flipping.
- **Execution**: The interface and its corresponding implementation that initiates and manages the program flow.

Table of Contents
-----------------

1. [Project Structure](#Project-Structure)
2. [User Input and Command Script](#User-Input-and-Command-Script)
3. [Run Application](#run-application)
4. [Design Changes and Justification](#design-changes-and-justification)
5. [Citations of images used for testing](#citations-of-images-used-for-testing)



# Project Structure

---

- **model/**: Contains classes representing the data and logic of the application.
    - `ImageModel.java`: Manages image metadata and pixel data.
    - `Operations.java`: Interface which defined the various operations to be performed.
    - `OperationsV2.java`:
      This updated Operations interface, extending the previous version, introduces new methods for the ImageProcessingProgram.
    - `ImageOperations.java`: Class that implements the Operations interface. Contains methods for various image processing operations.
    - `ImageOperationsV2.java` : This class extends the previous version, implementing the OperationsV2 interface with new methods: compression, histogramVisualization, colorCorrection, levelsAdjustment, and splitPreview.


- **controller/**: Contains the main logic files for handling commands and scripting for the package, as well as the classes for each and every command.
    - `ImageAppController.java` : Interface which defines the start operation, which needs to be carried out by Controller.
    - `CommandReader.java` : Reads and interprets script files  also manages the single line command provided for command execution and implements the ImageAppController. This has a startApplication function which basically starts the application. Also, this parse the input
      commands and gives the filter command to the CommandHandler class
    - `CommandHandler.java` : This class has a takes the parsed commands from CommandReader, and using the map redirect the command to the appropriate class design to handle the specified command.
    - `CommandExecuter.java`: Interface which defines the method need to be implemented by each command.
    - `AbstractCommandExecuter.java` : Abstract class to implement the command method between all command classes. All command Classes in the package extends this class. Contains
      implementation of common method of various command classes. This class implement the CommandExecuter interface.
    - `Blur.java`: Redirects the controller to the blur implementation in Model. Also checks if the command provided is correct or not. If not throws error.
    - `Brighten.java`: Redirects the controller to the brighten implementation in Model. Also checks if the command provided is correct or not. If not throws error.
    - `BrightnessComponent.java`: Redirect the controller to the getBrightnessComponent in Model. Also
      passes the appropriate handler based on input. The handler can be value, luma, or intensity. Value component is calculated by taking the max of the rgb values at each point and replace the max value in all channel at that point.
      Luma component is calculated by taking the weighted average of the rgb values at each point and replace the value in all channel at that point.
      Intensity component is calculated by taking the average of the rgb values at each point and replace the value in all channel at that point.
    - `ColorComponent.java`: Redirect the controller to the getColorComponent in Model. Also
      passes the appropriate color based on input. The handler can be 0,1 or 2 which are
      red, green or blue.
    - `HorizontalFlip.java`: Redirect the controller to the horizontalFlip implementation in Model. Check if the command is correct or not.
    -  `VerticalFlip.java` : Redirect the controller to the horizontalFlip implementation in Model. Check if the command is correct or not.
    - `Sepia.java` : Redirect the controller to the sepia implementation in Model. Check if the command is correct or not.
    -  `Sharpen.java` : Redirect the controller to the sharpen implementation in the Model. Check if the command is correct or not.
    -  `RBGCombine.java` : Redirect the controller to the rgbCombine implementation in Model. Check if the command is correct or not.
    -  `RBGSplit.java` : Redirect the controller to the rgbSplit implementation in Model. Check if the command is correct or not.
    -  `ColorCorrect.java` : Redirect the controller to the colorCorrection implementation in Model. Check if the command is correct or not.
    -  `LevelsAdjust.java` : Redirect the controller to the levelAdjustment implementation in Model. Check if the command is correct or not.
    -  `Histogram.java` : Redirect the controller to the histogram implementation in Model. Check if the command is correct or not.
    -  `Compress.java` : Redirect the controller to the compressImage implementation in Model. Check if the command is correct or not.
    - `Save.java`: The class obtains the matrix representation of image from Model. On the basis of the extension of the image which is to be saves the image gets saved at suitable filepath.
    - `Load.java `: The class converts any input format to a three dimension matrix to be passed to the Model.
  - `Quit.java `:  A class that performs the Quit operation. So when the user provides the quit command the program terminated.


- **view/**:
    - `ProgramView.java` : Interface for the view of this ImageProcessingProgram. It defines the setOutput method to display the output.
    - `ViewHandler.java` : This class implements the ProgramView interface and has an Appendable object to show the output of the program.


- **ImageProcessingApplication**: The entry point of the application. This is the main class where the controller and model objects are instantiated, and from here the controller starts to take the user input.



Class Description
--------------------

---

The application is based on MVC architecture
- **model** :  This package contains all the classes which are needed to manipulate and store metadata associated with each image.
    1. `Operations.java` : This interface represents the contract which defines the operations that can be performed on images, regardless of their specific types. The                     available methods include: retrieving components, flipping, brightening, splitting, combining, blurring, sharpening, and applying sepia filtering.

    2. `ImageOperations.java` : The ImageOperations class implements the Operations interface, providing implementations for various image manipulation and processing tasks. This class also maintains a map that stores instances of all images loaded and processed during the execution of the application, allowing efficient access and manipulation throughout the script's runtime.

    3. `ImageModel.java` : This class represents an image, so the class contains all the variables and method needed to handle an image. An image is a 3-d matrix of pixels.
    
    4. `OperationsV2.java` : New version of the operations interface that introduces new methods provided by the ImageProcessingProgram. It extends the older version Operations interface.
    
    5. `ImageOperationsV2.java` : This class implements the new methods needed to be implemented by the program. It extends the older class and implements the new OperationsV2 interface. It introduces the following new methods Compression,Histogram Visualization,Color Correction,Levels Adjustment,Split Preview.
    
- **controller** : This package contains all the classes which are needed to have a communication between model and view and is responsible for the I/O operations for the application.
    1. `CommandReader.java` : The CommandReader class is responsible for reading and executing commands from a script file as well as the ones provided in command line. It utilizes a CommandHandler to  process commands parsed from the file/command-line. This class provides methods to read commands from a specified file path, ensuring that the file is in the correct format and skips comments.

    2. `ImageAppController.java` : This is an interface which defines a method for processing commands. Implementing classes should provide the logic to handle the command input       into the system.

    3. `CommandHandler.java` : The CommandHandler class is responsible for managing various commands related to image processing. It utilizes a HashMap to associate command strings with their respective command classes, allowing for the dynamic creation of command objects using BiFunction.

    4. `CommandExecuter.java`: Interface which defines the method ( execute() ) to be implemented by each command. Other classes such as Load, Save, Brighten etc. in the package implement this interface.

    5. `AbstractCommandExecuter.java` : Abstract class to implement the common methods between all command classes. All Classes in the package extends this class.

- **view** :
    1. `ProgramView.java` : Interface for the view of this ImageProcessingProgram. It defines the setOutput method to display the output.
    2. `ViewHandler.java` : This class implements the ProgramView interface and has an Appendable object to show the output of the program.

## Class Diagram
Below is the class diagram of the application generated using the IntelliJ-IDE.

![Class Diagram](res/ClassDiagram.png)

Image Format Conversion
--------------------

In addition to standard image manipulation, the application supports interconversion between different image formats, enabling users to easily convert images from one format to another.

Multi-Image Processing
--------------------

The application can handle multiple images simultaneously, allowing users to load and process various images in one session.



User Input and Command Script
====

---
The user has two modes to use this application, either in interactive mode or in non-interactive mode.

### Non-Interactive Mode

In non-interactive mode, the user can directly provide a filepath of the script and using the following command, the application runs that script and automatically quits.
```
-file name-of-script.txt
```

### Interactive Mode
Users interact with the application by providing a script file that specifies a sequence of commands also user can provide the command in a single line through the command prompt. Each command must detail:

1. The operation to be performed on the image.
2. The path to the image file (supported formats: JPEG, PNG, and PPM (P-3)).
3. The name for the new image that will be generated as a result of the operation.

If the command provided is invalid or not found a suitable message would be provided to the user. The program only terminates when the user provides a quit command.

The structure of various commands is as follows:

1. **load image-path image-name**: Load an image from the specified path and refer it to henceforth in the program by the given image name.
2. **save image-path image-name**: Save the image with the given name to the specified path which should include the name of the file.
3. **red-component image-name dest-image-name**: Create an image with the red-component of the image with the given name.
4. **green-component image-name dest-image-name**: Create an image with the green-component of the image with the given name.
5. **blue-component image-name dest-image-name**: Create an image with the blue-component of the image with the given name.
6. **intensity-component image-name dest-image-name**: Create an image with the intensity-component of the image with the given name.
7. **value-component image-name dest-image-name**: Create an image with the value-component of the image with the given name.
8. **luma-component image-name dest-image-name**: Create an image with the luma-component of the image with the given name.
9. **horizontal-flip image-name dest-image-name**: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
10. **vertical-flip image-name dest-image-name**: Flip an image vertically to create a new image, referred to henceforth by the given destination name.
11. **brighten increment image-name dest-image-name**: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).
12. **rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue**: split the given image into three images containing its red, green and blue components respectively. These would be the same images that would be individually produced with the red-component, green-component and blue-component commands.
13. **rgb-combine image-name red-image green-image blue-image**: Combine the three images that are individually red, green and blue into a single image that gets its red, green and blue components from the three images respectively.
14. **blur image-name dest-image-name**: blur the given image and store the result in another image with the given name.
15. **sharpen image-name dest-image-name**: sharpen the given image and store the result in another image with the given name.
16. **sepia image-name dest-image-name**: produce a sepia-toned version of the given image and store the result in another image with the given name.
17. **compress percentage image-name dest-image-name**: compresses the specified image by the given percentage. The quality of the image may decrease as the compression percentage increases.
18. **histogram image-name dest-image-name**: This command generates a histogram for the given image. A histogram is a graph that represents the distribution of pixel intensities in the image, typically across color channels (red, green, blue) or brightness levels.
19. **color-correct image-name dest-image-name**: This command applies color correction to the specified image. Color correction adjusts the color balance to improve the appearance of an image, correcting issues such as underexposure, incorrect white balance, or color casting.
20. **levels-adjust b m w image-name dest-image-name**: his command adjusts the brightness levels in the specified image using the black, mid, and white values (b, m, and w). These values help define the darkest (black), midtone (gray), and lightest (white) parts of the image, effectively adjusting contrast and brightness.
21. **quit** : command by which the user can end the application.
    
**[Split Preview Commands]**

Following functionalities can also be viewed in split, that is a part of the image is manipulated and the other remains the same.
The syntax for its command is the user can just add "split p" after each of the commands.

For example:

**blur image-name dest-image-name split value**

Following are the list of commands that support split preview:
- **blur image-name dest-image-name split value**
- **sharpen image-name dest-image-name value**
- **sepia image-name dest-image-name value**
- **red-component image-name dest-image-name value**
- **green-component image-name dest-image-name value**
- **blue-component image-name dest-image-name value**
- **value-component image-name dest-image-name value**
- **luma-component image-name dest-image-name value**
- **intensity-component image-name dest-image-name value**
- **color-correct image-name dest-image-name value**
- **levels-adjust b m w image-name dest-image-name value**



The application also allows users to specify the directory where the processed images will be saved.

Command to run the script path with all the above-mentioned commands is:

**run script-file**: Load and run the script commands in the specified file.

Whenever the user provides the load, run or save command, the user must specify the absolute file path from where the image is to be read or stored or from where the script file is present. If the path provided 
is not a valid one then an error is thrown.



Error Handling
--------------
---
The system has robust error handling, throwing appropriate exceptions for:

- Invalid command inputs.
- Non-existent files or directories.
- Incorrect image formats or unsupported extensions.
- Invalid parameter count for commands.


Image Format Support
--------------------
---
The application supports the following image formats:

- **PPM**: Portable Pixmap Format.
- **JPG / JPEG**: Joint Photographic Experts Group format.
- **PNG**: Portable Network Graphics.

Note: PPM images are saved in the plain text format (P3).



Installation
--------------------
---
1. Clone the repository:
   ```bash
   git clone https://github.khoury.northeastern.edu/aryanshah295/Assignment-4/
   ```
2. Navigate to the project directory:
   ```
   cd repository
   ```


# Run Application

---

The user can run this application either via the command line or by using a script file. Below are the steps for each method.

The user can either:
1. Run the main class, `ImageProcessingApplication`, directly.
2. Run the provided `.jar` file located in the `res` folder.

 Prerequisites
--------------------
Before running the application, ensure:
- The **Java Development Kit (JDK)** is installed on your system. You can verify this by entering the following command in your terminal or command prompt:

    ```bash
    java -version
    ```

Running the JAR File
--------------------

---
1. **Navigate to the Project Directory**  
   Move to the root project directory (e.g., `Assignment-5`) containing the `src`, `test`, and `res` folders:

    ```
    cd path/to/Assignment-5
    ```

2. **Running in Interactive Mode**  
   To start the application interactively, use the following command:

    ```
    java -jar res/Assignment-4-MVC.jar
    ```

   After executing this command, you’ll see a prompt: `"Enter a command:"` where you can begin loading, applying operations, and saving images.


3. **Running in Non-Interactive Mode**  
   To run the application with a script file, use:

    ```
    java -jar res/Assignment-4-MVC.jar -file res/script.txt
    ```

    In our case we have kept the script.txt file in the res folder.

    **Note :** For the above command to execute successfully the command must be executed from the root folder only.


Running the ImageProcessingApplication Class
--------------------
---
1. **Navigate to the Project Directory**  
   Move to the root project directory:

    ```
    cd path/to/Assignment-5
    ```

2. **Compile the Java Files**  
   Compile all Java files with the following command:

    ```
    javac src/controller/*.java src/model/*.java src/view/*.java src/*.java
    ```

3. **Running in Non-Interactive Mode**  
   Provide a script file as an argument to run in non-interactive mode:

    ```
    java -cp src ImageProcessingApplication -file res/script.txt
    ```

4. **Running in Interactive Mode**  
   To run interactively, simply execute:

    ```
    java -cp src ImageProcessingApplication
    ```

Now, you can follow the on-screen instructions to operate the application.



### Command Line Interface

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
### Script Execution

1. Create a text file containing commands (e.g., `script.txt`)

2. Run `src/ImageProcessingApplication.java` file. The program will start and wait for user input.

3. Execute the script:


```
run script.txt
```
(NOTE: provide the absolute path of where script.txt is located in your system.)
Example: 
```
run D:/PDP/Assignment4/scripts/script.txt
```
**Note:** this command will only be executed in interactive mode.




# Design Changes and Justification

---

In the second phase of development for our Java-based image processing application, we expanded its functionality to support additional image operations, such as Compression, Histogram Equalization,Levels-adjustment and Color Correction. To accommodate these new features while preserving the existing design, we introduced modifications based on the MVC architecture and principles of extensibility.

Extended Operations Interface:
--------------------
1. We created a new interface, OperationsV2, which extends the existing Operations interface so we are using the concept of CodeReuse.
2. This extension allows us to add new method definitions required for the additional image processing operations without altering the original Operations interface.
3. By designing OperationsV2 as an extension, we ensure backward compatibility with the current interface, enabling future operations to build upon the original implementation seamlessly.
4. The private variables and methods of the ImageOperations class were changed to protected so that they can be inherited by the new ImageOperationsV2 class.


New ImageOperationsV2 Class:
--------------------
1. We developed ImageOperationsV2, a new concrete class that implements OperationsV2 and extends the functionality of the original ImageOperations class.
2. By extending ImageOperations, the new class inherits all previously implemented operations (e.g., blur, sharpen, sepia filter) while adding the logic for newly introduced operations such as compression, histogram visualization, color correction, levels adjustment and split preview.
3. Implementing the new operations directly in ImageOperationsV2 centralizes all image manipulation functionality, making it easier to maintain, test, and extend.

Justification for Extending Rather Than Modifying Existing Code:
--------------------
- This design ensures that our initial implementation remains stable and untouched, reducing the risk of introducing bugs to existing features.
- Any modules or components relying on ImageOperations or Operations remain unaffected, maintaining compatibility with previously developed parts of the application.
- Adopting this approach also aligns with the Open-Closed Principle, allowing our system to be extended with new functionality without modifying existing code.
- This structured approach to adding new features not only safeguards the original design but also provides a robust and extensible framework for future enhancements.


Changes in Controller
--------------------


In this phase, we modified the controller to support the new image operations introduced in `ImageOperationsV2`. Instead of initializing an `ImageOperations` object, we now initialize an `ImageOperationsV2` object and reference it through the `OperationsV2` interface. This allows the controller to seamlessly access both the existing and newly added functionalities.

1. **Updated Initialization**:
   - The controller now initializes `ImageOperationsV2` instead of `ImageOperations`. This update provides access to all original methods from `ImageOperations` as well as the new operations (such as Compression, Histogram Equalization, and Color Correction) implemented in `ImageOperationsV2`.
   - By referencing `ImageOperationsV2` through the `OperationsV2` interface, the controller maintains flexibility, enabling straightforward access to the extended set of operations without modifying the core controller logic.

2. **Consistency and Extensibility**:
   - Refactoring to use `OperationsV2` throughout the controller ensures that the application is prepared to handle any future extensions to image processing operations without requiring further changes to the controller.
   - This approach keeps the controller code consistent and makes it extensible, allowing for additional operations to be incorporated with minimal impact on the existing codebase.
   - Also as now new operations are to be supported to in the commandMap in the CommandHandler new key-value pairs are added for compress, histogram, levels-adjust and color-correct and their respective command-classes are added
     to the controller.
   
These changes enhance the controller’s capability to manage a broader range of image processing functionalities, making the application more versatile and adaptable to future requirements.



Changes in Main class
--------------------
- Added functionality of directly running a script file provided as an argument while running the Java or jar file.
- We have implemented this by reading the arguments provided and passing them to the controller as a Reader object.
- We also append a quit command at the end so that the program automatically quits after execution.



New Classes for Additional Operations
--------------------
We introduced dedicated classes within the controller package to implement the newly required operations in our application. Each new operation is encapsulated in its own class, allowing for clear separation of functionality and easier maintenance. These classes—`Histogram`, `ColorCorrect`, `LevelsAdjust`, and `Compress`—each call the respective methods implemented in the `ImageOperationsV2` class within the model, adhering to the MVC pattern.

1. **New Operation Classes in Controller**:
   - We added the following command classes:
     - **Histogram**: Handles histogram equalization operations by invoking the respective method in `ImageOperationsV2`.
     - **ColorCorrect**: Manages color correction functionality, linking to the `ImageOperationsV2` method for color correction.
     - **LevelsAdjust**: Adjusts the image levels (black, mid, white) by utilizing the `levelsAdjust` method from `ImageOperationsV2`.
     - **Compress**: Facilitates image compression operations, calling the corresponding method in `ImageOperationsV2`.


2. **Split Preview Functionality**:
   To add the functionality for new Split Preview operation, which is supported by blur, sharpen, sepia, color-components, brightness-components, color-correct and levels-adjust.
  - To implement this, we added new validation in those particular operations that if command length is 2 more than existing length, it checks for "split" keyword and the percentage by which we need to split.
  - Our application splits the original image by given percentage value and applies the operation only on this part.
  - It then merges this image and the remaining part of the original image using a regain() method.
   
3. **Responsibilities of Each Class**:
   - Each class acts as a dedicated handler for its specific image processing operation. By encapsulating the logic for calling `ImageOperationsV2` methods within these classes, we ensure that each operation has a single responsibility and is isolated from other processing logic.
   - This approach allows each operation to be tested independently, streamlining the addition or modification of individual operations as needed.

4. **Adherence to MVC Principles**:
   - The new classes follow the MVC architecture by maintaining a clean separation between the controller logic and model operations. By keeping the implementations of these operations in `ImageOperationsV2` (model) and only invoking them from the respective controller classes, we reinforce the controller's role as the intermediary between the view and model.
   
This modular approach simplifies future updates and enhances readability, maintainability, and scalability of the application’s codebase, preparing it for potential new operations or adjustments to existing functionality.

### View

- Added a view to this ImageProcessingApplication.
- This view is responsible for displaying the output messages and exceptions to the user.
- It is a simple command line view that prints out success messages on successful execution.
- It displays various exceptions such as "Invalid Command Length" when the syntax used by user is invalid.



# Citations of Images Used for Testing


---

 1. [`Bird.png`](https://www.dpreview.com/samples/3925134721/fujifilm-x-a3-sample-gallery)

 2. [`Koala.ppm`](https://animals-are-cool.fandom.com/wiki/Koala)

 3. [`Panda.jpeg`](https://www.freepik.com/photos/simple-watercolor-panda/2)
