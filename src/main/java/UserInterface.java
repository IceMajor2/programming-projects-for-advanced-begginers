
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.print("Welcome to ASCII Art Converter."
                + "\nImage file: ");
        String imgName = scanner.nextLine();
        int scaleDown = -1;
        int styleChoice = -1;
        boolean invertedStatus = false;

        while (true) {
            ImageReader reader = new ImageReader(ASCIIArt.PATH + "imgs\\" + imgName);
            int[][][] pixels = reader.getPixelArray();
            int height = pixels.length;
            int width = pixels[0].length;

            printMenu(invertedStatus);
            char input = scanner.next().charAt(0);
            scanner.nextLine();

            if (input == '0') {
                break;
            }
            if (input == '9') {
                System.out.print("New file is: ");
                imgName = scanner.nextLine();
                scaleDown = -1;
                continue;
            }
            if (input == '6') {
                invertedStatus = invertedStatus == false;
                continue;
            }
            if (scaleDown == -1 || input == '4') {
                System.out.print("Scale down the image (int only; preffered: "
                        + dimensionsHint(height, width) + "): ");
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
                    for (char ch : style) {
                        System.out.print(ch);
                    }
                    System.out.println("");
                }
                styleChoice = scanner.nextInt();
                scanner.nextLine();
                ASCIIArt.CHARS = ASCIIArt.STYLES[styleChoice - 1];
                if (input == '5') {
                    continue;
                }
            }

            PixelConverter converter = new PixelConverter(pixels, invertedStatus);
            char[][] chMatrix = converter.charMatrix(input);
            char[][] scaledArt = PixelConverter.scaleDown(chMatrix, scaleDown);

            String ascii = asciiInString(scaledArt);
            ASCIIImageFileCreator file = new ASCIIImageFileCreator(imgName,
                    input, styleChoice, invertedStatus);
            file.writeFile(ascii);
            System.out.println(ascii);
        }
    }

    private String asciiInString(char[][] arr) {
        StringBuilder output = new StringBuilder("");
        for (char[] row : arr) {
            for (char ch : row) {
                output.append(ch);
            }
            output.append("\n");
        }
        return output.toString();
    }

    private int dimensionsHint(int height, int width) {
        int divisor = 1;
        while ((height / divisor > 150 && width / divisor > 250)
                || (width / divisor > 150 && height / divisor > 250)) {
            divisor++;
        }
        return divisor;
    }

    public static char[][] getStyles() {
        char[] set01
                = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"
                        .toCharArray();
        char[] set02 = "█▓▒░".toCharArray();

        return new char[][]{set01, set02};
    }

    private void printMenu(boolean inverted) {
        System.out.println("'1' = ASCII_ART (AVERAGE)"
                + "\n'2' = ASCII_ART (MIN_MAX)"
                + "\n'3' = ASCII_ART (WEIGHTED AVERAGE)"
                + "\n'4' = scale down by..."
                + "\n'5' = change ASCII style"
                + "\n'6' = invert? (now: " + inverted + ")"
                + "\n'9' = change image"
                + "\n'0' = exit program");
    }
}
