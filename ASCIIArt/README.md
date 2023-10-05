# ASCII Art
This program recreates an image using characters thus resulting in a so-called *ASCII Art*.

There's an intuitive, self-explanatory command-line user interface.

Before executing the rendering command, you may:
* invert colors
* choose between 2 styles
* use different rendering alghoritms
* scale down the image

The text recreation will be available in the **`outputs`** folder that is located in the root directory.

Please note that the to-be-transformed images **have to** be put in the **`imgs`** folder which is - just like `outputs` - also located in the project's root directory.

## Use
Prerequisites:
* Java
* Maven

1. Launch `mvn compile` in the root directory. This should generate `target` directory with compiled classes.
2. Execute `java -cp ${PATH_TO_TARGET-CLASSES_DIR} ASCIIArt`. If you're in the root directory of the project, then the command should look like this: `java -cp ./target/classes ASCIIArt`.

## Have fun...
...and share the art!
