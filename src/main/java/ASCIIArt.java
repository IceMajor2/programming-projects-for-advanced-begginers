
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ASCIIArt {

    /*
    TODO: fix Java decimals error caused by multiplication in PixelConverter
          create UserInterface class
     */
    
    public static final char[][] STYLES = getStyles();
    public static final String PATH
            = "C:\\Users\\IceMajor\\Documents\\NetBeansProjects\\ASCIIArt\\";
    public static char[] CHARS = null;

    public static void main(String[] args) {

        System.out.print("Welcome to ASCII Art Converter."
                + "\nImage file: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        int scaleDown = -1;

        while (true) {
            ImageReader reader = null;
            try {
                reader = new ImageReader(PATH + "imgs\\" + fileName);
            } catch (IOException e) {
                System.out.println("Could not read file.");
                break;
            }

            System.out.println("How would you like to have brightness levels calculated?"
                    + "\n'1' = ASCII_ART (AVERAGE)"
                    + "\n'2' = ASCII_ART (MIN_MAX)"
                    + "\n'3' = ASCII_ART (WEIGHTED AVERAGE)"
                    + "\n'4' = scale down by..."
                    + "\n'9' = change image"
                    + "\n'0' = exit program");
            char input = scanner.next().charAt(0);
            scanner.nextLine();
            if (input == '9') {
                System.out.print("New file is: ");
                fileName = scanner.nextLine();
                scaleDown = -1;
                continue;
            }
            if (input == '4' || scaleDown == -1) {
                System.out.print("Scale down the image (int only) ");
                scaleDown = Integer.valueOf(scanner.nextLine());
                if (input == '4') {
                    continue;
                }
            }
            if (input == '0') {
                break;
            }
            if (CHARS == null) {
                System.out.println("Determine the style. Options:");
                for (int i = 0; i < STYLES.length; i++) {
                    System.out.print(i + ". -> ");
                    for (int y = 0; y < STYLES[i].length; y++) {
                        System.out.print(STYLES[i][y]);
                    }
                    System.out.println("");
                }
                char styleChoice = scanner.next().charAt(0);
                scanner.nextLine();
            }

            int[][][] pixels = reader.getPixelArray();
            PixelConverter converter = new PixelConverter(pixels);
            char[][] chMatrix = converter.charMatrix(input);
            char[][] scaledArt = PixelConverter.scaleDown(chMatrix, scaleDown);

            String ascii = asciiInString(scaledArt);
            outputToFile(outputFileName(fileName, input), ascii);
            System.out.println(ascii);
        }
    }

    public static void outputToFile(String fileName, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(PATH + "\\outputs\\" + fileName);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String outputFileName(String img, char choice) {
        StringBuilder outputFile = new StringBuilder(img);
        outputFile.delete(outputFile.lastIndexOf("."), outputFile.length());
        outputFile.append(choice);
        outputFile.append(".txt");
        return outputFile.toString();
    }

    public static String asciiInString(char[][] arr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < arr.length; i++) {
            for (int y = 0; y < arr[0].length; y++) {
                output.append(arr[i][y]);
            }
            output.append("\n");
        }
        return output.toString();
    }

    public static char[][] getStyles() {
        char[] set01
                = ("`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$")
                        .toCharArray();
        char[] set02 = {};

        return new char[][]{set01, set02};
    }
}
