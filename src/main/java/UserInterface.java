
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class UserInterface {

    private Scanner scanner;
    
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.print("Welcome to ASCII Art Converter."
                + "\nImage file: ");
        String fileName = scanner.nextLine();
        int scaleDown = -1;

        while (true) {
            ImageReader reader = new ImageReader(ASCIIArt.PATH + "imgs\\" + fileName);

            System.out.println("How would you like to have brightness levels calculated?"
                    + "\n'1' = ASCII_ART (AVERAGE)"
                    + "\n'2' = ASCII_ART (MIN_MAX)"
                    + "\n'3' = ASCII_ART (WEIGHTED AVERAGE)"
                    + "\n'4' = scale down by..."
                    + "\n'5' = change ASCII style"
                    + "\n'9' = change image"
                    + "\n'0' = exit program");
            char input = scanner.next().charAt(0);
            scanner.nextLine();
            if (input == '0') {
                break;
            }
            if (input == '9') {
                System.out.print("New file is: ");
                fileName = scanner.nextLine();
                scaleDown = -1;
                continue;
            }
            if (scaleDown == -1 || input == '4') {
                System.out.print("Scale down the image (int only): ");
                scaleDown = Integer.valueOf(scanner.nextLine());
                if (input == '4') {
                    continue;
                }
            }
            if (ASCIIArt.CHARS == null || input == '5') {
                System.out.println("Determine the style. Options:");
                for (int i = 0; i < ASCIIArt.STYLES.length; i++) {
                    System.out.print((i + 1) + ". -> ");
                    
                    char[] style = ASCIIArt.STYLES[i];
                    for(char ch : style) {
                        System.out.print(ch);
                    }
                    System.out.println("");
                }
                int styleChoice = scanner.nextInt() - 1;
                scanner.nextLine();
                ASCIIArt.CHARS = ASCIIArt.STYLES[styleChoice];
                if(input == '5') {
                    continue;
                }
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

    public void outputToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(
        ASCIIArt.PATH + "\\outputs\\" + fileName)) {
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String outputFileName(String img, char choice) {
        StringBuilder outputFile = new StringBuilder(img);
        outputFile.delete(outputFile.lastIndexOf("."), outputFile.length());
        outputFile.append(choice);
        outputFile.append(".txt");
        return outputFile.toString();
    }

    public String asciiInString(char[][] arr) {
        StringBuilder output = new StringBuilder("");
        for(char[] row : arr) {
            for(char ch : row) {
                output.append(ch);
            }
            output.append("\n");
        }
        return output.toString();
    }

    public static char[][] getStyles() {
        char[] set01
                = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"
                        .toCharArray();
        char[] set02 = "█▓▒░".toCharArray();

        return new char[][]{set01, set02};
    }
}
