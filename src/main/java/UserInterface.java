
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private ProgramLogic logic;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.logic = new ProgramLogic();
    }

    public void start() throws IOException {
        System.out.print("Welcome to ASCII Art Converter."
                + "\nImage file: ");
        String imgName = scanner.nextLine();
        int scaleDown = -1;
        int styleChoice = -1;
        boolean invertedStatus = false;

        while (true) {

            logic.readImage(ASCIIArt.PATH + "imgs\\" + imgName);

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
                scaleDown = newScale();
                if (input == '4') {
                    continue;
                }
            }
            if (ASCIIArt.CHARS == null || input == '5') {
                styleChoice = newStyle();
                if (input == '5') {
                    continue;
                }
            }
            String ascii = logic.convertImage(input, scaleDown, invertedStatus);
            ASCIIImageFileCreator file = new ASCIIImageFileCreator(imgName,
                    input, styleChoice, invertedStatus);
            file.writeFile(ascii);
            System.out.println(ascii);
        }
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

    private void printStyle(int i) {
        char[] style = ASCIIArt.STYLES[i];
        for (char ch : style) {
            System.out.print(ch);
        }
        System.out.println("");
    }

    private int newScale() {
        System.out.print("Scale down the image (int only; preffered: "
                + logic.dimensionsHint() + "): ");
        return Integer.valueOf(scanner.nextLine());
    }

    private int newStyle() {
        System.out.println("Determine the style. Options:");
        for (int i = 0; i < ASCIIArt.STYLES.length; i++) {
            System.out.print((i + 1) + ". -> ");
            printStyle(i);
        }
        int styleChoice = scanner.nextInt();
        scanner.nextLine();
        logic.setStyle(styleChoice - 1);
        return styleChoice;
    }
}
