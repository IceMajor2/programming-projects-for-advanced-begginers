
import java.io.IOException;
import java.io.File;

public class ProgramLogic {

    private ImageReader reader;

    public ProgramLogic() {

    }

    public void readImage(String path) throws IOException {
        this.reader = new ImageReader(path);
    }

    public String convertImage(char alghoritm, int scaleDown, boolean invert) {
        int[][][] pixels = reader.getPixelArray();
        PixelConverter converter = new PixelConverter(pixels, invert);
        char[][] chMatrix = converter.charMatrix(alghoritm);
        char[][] scaledArt = PixelConverter.scaleDown(chMatrix, scaleDown);
        String ascii = asciiInString(scaledArt);
        return ascii;
    }

    public void setStyle(int style) {
        ASCIIArt.CHARS = ASCIIArt.STYLES[style];
    }

    public String asciiInString(char[][] arr) {
        StringBuilder output = new StringBuilder("");
        for (char[] row : arr) {
            for (char ch : row) {
                output.append(ch);
            }
            output.append("\n");
        }
        return output.toString();
    }

    public int dimensionsHint() {
        int height = reader.getHeight();
        int width = reader.getWidth();

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
    
    public void listFiles(String path) {
        File files = new File(path);
        for(String file : files.list()) {
            if(file.equals(".gitignore")) {
                continue;
            }
            System.out.println(file);
        }
    }
}
