package photomosaics;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
TODO:
    - there's no point in having more than 60x60 source images, think about sizing them down
    - The database should be loading and/or deleting only those files that need to be. There's no point in writing the whole yml file.
 */
public class Photomosaics {

    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);
    public static String PATH_TO_DATASET = String.format("%s%s%s%s", "pictures",
            File.separator, "dataset", File.separator);
    public static String DIRECT_PATH_TO_VALUES = String.format("%s%s%s%s%s%s%s",
            "src", File.separator, "main", File.separator, "resources",
            File.separator, "dataset_RGB.yml");
    public static Map<File, int[]> IMGS_COLORS = null;

    public static void main(String[] args) throws IOException {
        DataHandler.load();
    }
}
