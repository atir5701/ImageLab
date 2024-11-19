# Image Processing Application - USEME.md

---

This file provides instructions on how to operate the Image Processing Application. You can utilize this application through Command Line Interface (CLI) and Graphical based User Interface (GUI).

This application allows users to process images by running commands directly from a command prompt or through a script file. It supports a wide range of operations, including color manipulation, image transformations, and enhancement effects. Users can also preview split views of specific manipulations, where only part of the image is modified.


The application offers a graphical user interface (GUI) that complements its command-line capabilities, providing users with an intuitive and user-friendly way to interact with the system. Through the GUI, users can effortlessly load images, save them in various formats, and perform all the operations available in the command-line interface, such as applying filters or modifying image properties.

Moreover, the GUI introduces an exclusive feature: Image Downscaling, allowing users to resize images to smaller dimensions while maintaining their aspect ratio. This operation is not available through the command-line interface, making the GUI a more versatile and powerful tool for image processing tasks.

This application features a command-based interface for executing various image processing operations. Below is an overview of the available commands, their functionality, examples of how to use them, and any applicable conditions or dependencies.

# Table of Contents

1. [Command Line Based Interaction](#Commandline-Based-Interaction-with)
   - [Command Syntax and Structure](#Command-Syntax-and-Structure)
   - [Supported Commands](#Supported-Commands)
   - [Split Preview Mode](#Split-Preview-Mode)
   - [Example](#Example-Workflow)
4. [Graphical User Interface (GUI)](#graphical-user-interface-gui)
   - [Features](#features)
   - [Exclusive Features in GUI](#exclusive-features-in-gui)
5. [Error Handling](#error-handling)
6. [FAQ](#faq)
7. [Troubleshooting](#troubleshooting)
8. [License](#license)

# Command line Based Interaction 
All the sections below provide detailed information on how to use the program in command-line mode. 

## Command Syntax and Structure

Each command has a specific format and requires certain arguments. Commands are summarized below. 

**Note :** Users must strictly adhere to the exact format for all commands described below. Any deviation or formatting error may lead to execution failures. Additionally, altering the order of arguments will result in unexpected outcomes.

**Note :** An image must be loaded before applying any operations, and the `save` command should be used to save any processed image to your system.

Here’s an enhanced version of the **Supported Commands** section with more detailed descriptions and additional instructions.


### Supported Commands
---

Each command must adhere strictly to the specified syntax. If any command is incorrect or the image is not in the specified file path, the application will throw an exception. Additionally, for any operation to work on an image, it must already be loaded into the application’s memory with the specified name.



#### 1. **Loading and Saving**

- **Load**: Loads an image from the provided absolute file path and assigns a custom name for in-app reference.
  
  This image name is crucial for subsequent operations, as it allows the application to identify which image to manipulate.

  **It is mandatory to load an image first in order to use any following commands.**

  Ensure the image exists in the specified path; otherwise, an exception will be thrown.

  The application only supports images in **PNG, JPG, JPEG, and PPM** formats. If an image with a different extension is provided, an error will be thrown.

  ```plaintext
  load absolute/path/to/image.jpg image-name
  ```
  **Example**:
  ```plaintext
  load /images/sample.jpg sample
  ```
  

- **Save**: Saves the processed image to a specified path with the provided name.
  Make sure the image you want to save has been processed and exists in the application’s memory.

  Ensure the filepath is valid; otherwise, an exception will be thrown.

  The application only supports images in **PNG, JPG, JPEG, and PPM** formats. If an image with a different extension is provided, an error will be thrown.
  ```plaintext
  save absolute/path/to/output.png image-name
  ```
  **Example**:
  ```plaintext
  save /results/sample-output.png sample
  ```
  

#### 2. **Color and GreyScale Components**

These commands isolate individual color channels (Red, Green, Blue) or specific grayscale components (Intensity, Value, Luma) of an image.

- **Red/Green/Blue Component**: Extracts a specific color component (red, green, or blue) from the image and saves the result under a new name. The output image will contain only the selected color channel information and in the other channels the value of the selected channels will be set.\
  So for red-component of the image values will be (R,R,R) ; for the green-component image value will be (G,G,G) and for blue-component image value will be (B,B,B).

  **It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).

  This operation also supports `partial image manipulation`, which requires a mask image to be loaded into the system. Initially, the operation is applied to the entire image. Then, the mask image guides the final manipulation: regions of the mask containing black pixels indicate where the resulting values from the operation should be applied in the final image.

  For the operation to be carried out successfully, the mask image must be loaded into the system, and its dimensions must match those of the image on which the operation is to be performed.
  
  Application will store output image into its memory. User can use `save` command to get the output image.
  
  ```plaintext
  red-component image-name dest-image-name
  green-component image-name dest-image-name
  blue-component image-name dest-image-name
  ```
  **Example**:
  ```plaintext
  red-component sample sample-red
  green-component sample sample-green
  blue-component sample sample-blue
  ```

  The commands with the split operations are.

  ```plaintext
  red-component image-name dest-image-name split percentage
  green-component image-name dest-image-name split percentage
  blue-component image-name dest-image-name split percentage
  ```

  **Example**:
  ```plaintext
  red-component sample sample-red split 58.9
  green-component sample sample-green split 34
  blue-component sample sample-blue split 89
  ```

  The command with partial image manipulation are.

    ```plaintext
  red-component image-name mask-image-name dest-image-name
  green-component image-name mask-image-name dest-image-name
  blue-component image-name mask-image-name dest-image-name
  ```

  **Example**:
  ```plaintext
  red-component sample sample-red-mask sample-red
  green-component sample sample-green-mask sample-green
  blue-component sample sample-blue-mask sample-blue
  ```

- **Intensity/Value/Luma Component**: Creates a grayscale image based on different brightness calculations: 
   
  *Intensity* averages the RGB values.
    
  *Value* uses the highest RGB value.
    
  *Luma* uses a weighted sum of the RBG values.


  **It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).

  This operation also supports `partial image manipulation`, which requires a mask image to be loaded into the system. Initially, the operation is applied to the entire image. Then, the mask image guides the final manipulation: regions of the mask containing black pixels indicate where the resulting values from the operation should be applied in the final image.

  For the operation to be carried out successfully, the mask image must be loaded into the system, and its dimensions must match those of the image on which the operation is to be performed.
  
  Application will store output image into its memory. User can use `save` command to get the output image.
    
  ```plaintext
    value-component image-name dest-image-name
    intensity-component image-name dest-image-name
    luma-component image-name dest-image-name
    ```
    **Example**:
    ```plaintext
    value-component sample sample-red
    intensity-component sample sample-intensity
    luma-component sample sample-luma
    ```
  The commands with the split operations are.

  ```plaintext
    value-component image-name dest-image-name split percentage
    intensity-component image-name dest-image-name split percentage
    luma-component image-name dest-image-name split percentage
    ```

    **Example**:
    ```plaintext
    value-component sample sample-value split 58.9
    intensity-component sample sample-intensity split 34
    luma-component sample sample-luma split 89
    ```

  The commands with the partial image manipulation.
  
  ```plaintext
    value-component image-name masked-image-name dest-image-name 
    intensity-component image-name masked-image-name dest-image-name 
    luma-component image-name masked-image-name dest-image-name
    ```

    **Example**:
    ```plaintext
    value-component sample sample-value-masked sample-value
    intensity-component sample sample-intensity-masked sample-intensity-masked
    luma-component sample sample-luma-masked sample-luma
    ```
  


#### 3. **Transformations**

- **Horizontal Flip**: Flips the image horizontally, creating a mirror image along the vertical axis.

  **It is mandatory that given input image name should be present in the application's memory.**

  Application will store output image into its memory. User can use `save` command to get the output image.

  ```plaintext
  horizontal-flip image-name dest-image-name
  ```
  **Example**:
  ```plaintext
  horizontal-flip sample sample-flipped
  ```

- **Vertical Flip**: Flips the image vertically, creating a mirror image along the horizontal axis.

  **It is mandatory that given input image name should be present in the application's memory.**

  Application will store output image into its memory. User can use `save` command to get the output image.
  
- ```plaintext
  vertical-flip image-name dest-image-name
  ```
  **Example**:
  ```plaintext
  vertical-flip sample sample-flipped
  ```

#### 4. **Brightness Adjustment**

- **Brighten** or **Darken**: Adjusts the brightness of an image by a specified increment (positive for brightening, negative for darkening).
 
  **It is mandatory that given input image name should be present in the application's memory.**
  
  The brightness value affects all color channels equally.

  The increment value the user can enter should be an integer.
  
  User can enter negative increment value to darken the image.

  Application will store output image into its memory. User can use `save` command to get the output image.

  ```plaintext
  brighten increment image-name dest-image-name
  ```
  **Example for Brighten command **:
  ```plaintext
  brighten 20 sample sample-brightened
  ```
  
  **Example for Darken command **:
  ```plaintext
  brighten -20 sample sample-brightened
  ```
  

#### 5. **RGB Split and Combine**

- **RGB Split**: Separates the image into three separate images for each color channel (red, green, blue).
  
  **It is mandatory that given input image name should be present in the application's memory.**
  
  Application will store output images into its memory. User can use `save` command to get the output images.

  ```plaintext
  rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
  ```

    **Example**:
    ```plaintext
    rgb-split sample sample-red sample-green sample-blue
    ```

- **RGB Combine**: Combines three images, each containing one of the RGB color channels, into a single image.
  
  All the input images must have **the same dimensions**.

  Order of the input images **must not change**.

  And to generate the correct image the ordering of the images to be combined must be maintained, that is first the red image, then green and at last the blue image must be provided.

  
  ```plaintext
  rgb-combine image-name red-image green-image blue-image
  ```
    **Example**:
    ```plaintext
    rgb-combine sample-new sample-red sample-green sample-blue
    ```

#### 6. **Blur and Sharpen**

- **Blur**: Applies a blurring effect to soften the image, reducing details and smoothing transitions.
  
  It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).
  
  This operation also supports `partial image manipulation`, which requires a mask image to be loaded into the system. Initially, the operation is applied to the entire image. Then, the mask image guides the final manipulation: regions of the mask containing black pixels indicate where the resulting values from the operation should be applied in the final image.

  For the operation to be carried out successfully, the mask image must be loaded into the system, and its dimensions must match those of the image on which the operation is to be performed.
  

  Application will store output image into its memory. User can use `save` command to get the output image.

 

  ```plaintext
  blur image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    blur sample sample-blur
    ```
  The blur command with split parameter is given as.
    ```plaintext
  blur image-name dest-image-name split percentage
  ```
  **Example**:
  ```plaintext
  blur sample sample-blur split 45.7
  ```

  The blur command with partial image manipulation.
    ```plaintext
  blur image-name masked-image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    blur sample sample-masked sample-blur
    ```
  
  

- **Sharpen**: Enhances the edges in an image, making details crisper.
  
  **It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).

   This operation also supports `partial image manipulation`, which requires a mask image to be loaded into the system. Initially, the operation is applied to the entire image. Then, the mask image guides the final manipulation: regions of the mask containing black pixels indicate where the resulting values from the operation should be applied in the final image.

  For the operation to be carried out successfully, the mask image must be loaded into the system, and its dimensions must match those of the image on which the operation is to be performed.
  

  Application will store output image into its memory. User can use `save` command to get the output image.
  
  ```plaintext
  sharpen image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    sharpen sample sample-sharpen
    ```
  The sharpen command with split parameter is given as.
    ```plaintext
  sharpen image-name dest-image-name split percentage
  ```
  **Example**:
  ```plaintext
  sharpen sample sample-sharpen split 45.7
  ```

  The sharpen command with partial image manipulation.
    ```plaintext
  sharpen image-name masked-image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    sharpen sample sample-masked sample-sharpen
    ```
  
#### 7. **Sepia Tone**

- **Sepia**: Applies a sepia filter to give the image a warm, aged appearance.
  
  **It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).

   This operation also supports `partial image manipulation`, which requires a mask image to be loaded into the system. Initially, the operation is applied to the entire image. Then, the mask image guides the final manipulation: regions of the mask containing black pixels indicate where the resulting values from the operation should be applied in the final image.

  For the operation to be carried out successfully, the mask image must be loaded into the system, and its dimensions must match those of the image on which the operation is to be performed.
  
  
  Application will store output image into its memory. User can use `save` command to get the output image.
  
  ```plaintext
  sepia image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    sepia sample sample-sharpen
    ```
  The Sepia command with split parameter is given as.
    ```plaintext
  sepia image-name dest-image-name split percentage
  ```
  
  **Example**:
  ```plaintext
  sepia sample sample-sharpen split 45.7
  ```

  The sepia command with partial image manipulation.
    ```plaintext
  sepia image-name masked-image-name dest-image-name
  ```
  **Example**:
    ```plaintext
    sepia sample sample-masked sample-sepia
    ```

#### 8. **Compression**

- **Compress**: Reduces the file size of an image by a specified percentage, maintaining resolution but potentially affecting quality. The percentage parameter should lie between 0 and 100. It can be a decimal value as well.

  Value of percentage must be within  **0 and 100** and (decimal values are allowed).

  Application will store output image into its memory. User can use `save` command to get the output image.


  ```plaintext
    compress percentage image-name dest-image-name
    ```
  **Example**:
    ```plaintext
    compress 56.8 sample sample-sharpen
    ```

#### 9. **Histogram**

- **Histogram**: Generates a histogram of the image’s color distribution.

  **It is mandatory that given input image name should be present in the application's memory.**

  Application will store output image (histogram) into its memory. User can use `save` command to get the output image.

  ```plaintext
  histogram image-name dest-image-name
  ```
  **Example**:
  ```plaintext
  histogram sample sample-hist
  ```

#### 10. **Color Correct**: 
- **Color Correct**: *Automatically adjusts colors in the image to improve balance and tone.
  
  **It is mandatory that given input image name should be present in the application's memory.**
  
  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).

  Application will store output image into its memory. User can use `save` command to get the output image.
 

  ```plaintext
    color-correct image-name dest-image-name
    ```
  **Example**:
    ```plaintext
    color-correct sample sample-color-correct
    ```
  The color-correct command with split parameter is given as.
  ```plaintext
  color-correct image-name dest-image-name split percentage
    ```

  **Example**:
    ```plaintext
    color-correct sample sample-color-correct split 57.9
    ```

#### 11. **Levels Adjustment**

- **Levels Adjust**: Adjusts black, mid, and white points to improve contrast and brightness. The black mid and white values provided should lie in 0-255 range. Also, the black, mid and white values should be in ascending order.

  **It is mandatory that given input image name should be present in the application's memory.**
  
  Values of `b`, `m` and `w` should be **an integer** and between **0 and 255**.

  Order of `b`, `m` and `w` **must not change**. 
  
  Values of `b`, `m` and `w` should be in ascending order (b < m < w).

  When used with the `split` argument, the application will apply the operation only to the specified percentage of the image, starting from the left side.

  Value of split percentage must be within  **0 and 100** and (decimal values are allowed).
  
  Application will store output image into its memory. User can use `save` command to get the output image.

  ```plaintext
    levels-adjust b m w image-name dest-image-name
    ```

  **Example**:
    ```plaintext
    levels-adjust 20 100 245 sample sample-level
    ```
  The levels-adjust command with split parameter is given as.
  ```plaintext
  levels-adjust b m w image-name dest-image-name split percentage
    ```

  **Example**:
    ```plaintext
    levels-adjust 20 100 245 sample sample-level split 57.9
    ```


#### 12. **Quit Application**

- **Quit**: Exits the application.
  When the application is closed, it will lose its memory, resulting in all loaded and processed images being discarded.
  
  ```plaintext
    quit
    ```

#### 13. **Run Script File**

- **Run Script**: Executes a list of commands from a script file. Each command in the script must follow the specified syntax.

  Script file must be **a text file**.

  Path of the script file must be valid.

  If an invalid command is encountered in the script file, an error message for that specific command will be displayed, and the script will proceed to execute the remaining commands.

  Application will store output images into its memory. User can use `save` command to get the output images.  

  ```plaintext
    run absolute/path/to/script.txt
    ```

  **Example**:
    ```plaintext
    run res/script.txt
    ```

### Split Preview Mode

Certain commands support **Split Preview** mode, where only part of the image is modified while the rest remains unchanged. To use split preview, append `split p` to the command. The 'p' parameter provided by the user should lie in 0-100 range, it can be a decimal value. Supported commands for split preview include:

- `blur`, `sharpen`, `sepia`, `red-component`, `green-component`, `blue-component`, `value-component`, `luma-component`, `intensity-component`, `color-correct`, `levels-adjust`.

**Example**:
```plaintext
blur sample sample-blur split p
```

**Note**: Commands in Split Preview mode should strictly follow the syntax above to avoid errors.


## Example Workflow

1. **Load** an image:
   ```plaintext
   load /images/photo.png photo
   ```

2. **Apply operations**:
   ```plaintext
   sepia photo photo-sepia
   ```

3. **Save** the modified image:
   ```plaintext
   save /results/photo-sepia.png photo-sepia
   ```

4. **Quit** the application:
   ```plaintext
   quit
   ```

# Graphical User Interface Based Interaction 
All the sections below explain how the program operates using the graphical user interface (GUI).




This `USEME.md` file provides a comprehensive guide to all commands, syntax, and examples. Follow each step and condition carefully to ensure a smooth experience with the application.
