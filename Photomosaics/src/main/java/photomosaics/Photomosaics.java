package photomosaics;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
TODO:
I should probably load RGB values of each source image into, if not database, then at least an YAML or JSON file
 */
public class Photomosaics {

    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);
    public static String PATH_TO_DATASET = String.format("%s%s%s%s", "pictures",
            File.separator, "dataset", File.separator);
    public static Map<String, int[]> IMGS_COLORS = DataHandler.loadColorValues();

    public static void main(String[] args) throws IOException {
        BufferedImage input = ImageIO.read(new File(PATH_TO_INPUT + "nature.jpg"));
        int pxGroupSize = ImageHandler.calculatePxGroupDimension(input);
        int[] firstPxGroup = ImageHandler.pixelGroup(input, pxGroupSize)[0][0];
        String similar = ImageHandler.getMostSimilarImage(firstPxGroup);
        BufferedImage closest = ImageIO.read(new File(PATH_TO_DATASET + "cropped" + File.separator + similar));
        System.out.println(similar + " + difference: " + ImageHandler.distanceBetweenColors(firstPxGroup, IMGS_COLORS.get(similar)));
    }
}
