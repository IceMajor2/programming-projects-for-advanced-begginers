
import java.util.Arrays;

public class PixelConverter {

    private int[][][] pixels;
    private boolean invert;

    public PixelConverter(int[][][] pixels, boolean invert) {
        this.pixels = pixels;
        this.invert = invert;
    }

    private double brightnessLevel(int[] pixel, char choice) {
        double result = 0;
        switch(choice) {
            case '1':
                result = (pixel[0] + pixel[1] + pixel[2]) / 3.0;
                break;
            case '2':
                int min = Arrays.stream(pixel).min().getAsInt();
                int max = Arrays.stream(pixel).max().getAsInt();
                result = (max + min) / 2;
                break;
            case '3':
                result = pixel[0] * 0.21 + pixel[1] * 0.72 + pixel[2] * 0.07;
                break;
            default:
                throw new IllegalArgumentException();
        }
        if(invert) {
            result = 255 - result;
        }
        return result;       
    }

    private double[][] brightnessMatrix(char choice) {
        double[][] pxBrightness = new double[pixels.length][pixels[0].length];

        for (int i = 0; i < pixels.length; i++) {
            for (int y = 0; y < pixels[0].length; y++) {
                int[] pixel = pixels[i][y];
                double brightness = brightnessLevel(pixel, choice);
                pxBrightness[i][y] = brightness;
            }
        }
        return pxBrightness;
    }

    private char toChar(double brightness, double interspace) {
        int index = (int) (brightness / interspace);
        try {
            char ch = ASCIIArt.CHARS[index];
            return ch;
        } catch(ArrayIndexOutOfBoundsException e) {
            char ch = ASCIIArt.CHARS[ASCIIArt.CHARS.length - 1];
            return ch;
        }
    }

    public char[][] charMatrix(char choice) {
        char[][] chMatrix = new char[pixels.length][pixels[0].length * 3];
        double[][] brightnessMatrix = brightnessMatrix(choice);

        double interspace = 255.0 / ASCIIArt.CHARS.length;

        for (int i = 0; i < chMatrix.length; i++) {
            for (int y = 0; y < chMatrix[0].length; y += 3) {
                double pxBrightness = brightnessMatrix[i][y / 3];
                char ch = toChar(pxBrightness, interspace);
                chMatrix[i][y] = ch;
                chMatrix[i][y + 1] = ch;
                chMatrix[i][y + 2] = ch;
            }
        }
        return chMatrix;
    }
    
    public static char[][] scaleDown(char[][] pixels, int by) {
        char[][] newPic = new char[pixels.length / by][pixels[0].length / by];

        for (int i = 0, a = 0; i < newPic.length; i++, a += by) {
            for (int y = 0, b = 0; y < newPic[0].length; y++, b += by) {
                newPic[i][y] = pixels[a][b];
            }
        }
        return newPic;
    }  
}
