package photomosaics;

import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Photomosaics {

    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);
    public static BufferedImage picSource;

    public static void main(String[] args) throws IOException {
        picSource = ImageIO.read(new File(PATH_TO_INPUT + "nature.jpg"));

        // just a reprint
        ImageIO.write(picSource, "jpg", new File(PATH_TO_OUTPUT + "originalReprint.jpg"));

        // print dimensions of img
        System.out.println(String.format("HEIGHT x WIDTH = %d x %d",
                picSource.getHeight(), picSource.getWidth()));

        // scale down by 2
        BufferedImage shrinkedBy2
                = Scalr.resize(picSource, Scalr.Method.ULTRA_QUALITY,
                        picSource.getWidth() / 2, picSource.getHeight() / 2);
        ImageIO.write(shrinkedBy2, "jpg", new File(PATH_TO_OUTPUT + "shrinked.jpg"));

        // flip horizontally
        BufferedImage rotated = Scalr.rotate(picSource, Scalr.Rotation.CW_180);
        ImageIO.write(rotated, "jpg", new File(PATH_TO_OUTPUT + "rotated180.jpg"));

        // draw a rectangle on img
        BufferedImage copyOfSrc = ImageIO.read(new File(PATH_TO_INPUT + "nature.jpg"));
        Graphics2D g = copyOfSrc.createGraphics();
        g.setColor(Color.red);
        int squareSide = 500;
        int posX = (copyOfSrc.getWidth() / 2) - (squareSide / 2);
        int posY = (copyOfSrc.getHeight() / 2) - (squareSide / 2);
        g.fill(new Rectangle(posX, posY, squareSide, squareSide));
        g.dispose();
        ImageIO.write(copyOfSrc, "jpg", new File(PATH_TO_OUTPUT + "rect.jpg"));
    }
}
