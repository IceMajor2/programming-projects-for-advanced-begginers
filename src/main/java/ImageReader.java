
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class ImageReader {

    private BufferedImage image;
    private int height;
    private int width;

    public ImageReader(String imgPath) throws IOException {
        this.image = ImageIO.read(new File(imgPath));
        this.height = this.image.getHeight();
        this.width = this.image.getWidth();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][][] getPixelArray() {
        int[][][] pixels = new int[height][width][3];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                pixels[y][x][0] = red;
                pixels[y][x][1] = green;
                pixels[y][x][2] = blue;
            }
        }
        return pixels;
    }
}
