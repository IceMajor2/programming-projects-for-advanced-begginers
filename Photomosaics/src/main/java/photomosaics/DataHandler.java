package photomosaics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import static photomosaics.Photomosaics.PATH_TO_DATASET;
import static photomosaics.Photomosaics.PATH_TO_OUTPUT;
import static photomosaics.Photomosaics.IMGS_COLORS;
import org.yaml.snakeyaml.Yaml;

public class DataHandler {

    public static void outputImg(BufferedImage img, String imgName, String format) throws IOException {
        ImageIO.write(img, format, new File(PATH_TO_OUTPUT + imgName));
    }

    public static void outputImg(BufferedImage img, String imgName) throws IOException {
        outputImg(img, imgName, "jpg");
    }

    public static BufferedImage copy(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public static File[] croppedDataset() {
        File dir = new File(PATH_TO_DATASET + "cropped" + File.separator);
        File[] cropped = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return pathname.isFile();
            }
        });
        return cropped;
    }

    public static Map<String, int[]> loadColorValues() {
        Map<String, int[]> colorValues = new HashMap<>();
        File[] imgs = croppedDataset();

        for (File imgFile : imgs) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException e) {
                System.out.println(e);
            }
            int groupDim = img.getHeight();
            int[] colorVal = ImageHandler.pixelGroup(img, groupDim)[0][0];

            colorValues.put(imgFile.getName(), colorVal);
        }

        return colorValues;
    }

    public static void loadData() {
        
    }
    
    public static void writeYaml() {
        Map<String, int[]> values = loadColorValues();
        File yamlFile = new File(String.format("%s%s%s%s%s%s%s", "src", File.separator, "main",
                File.separator, "resources", File.separator, "dataset_RGB.yml"));
        try {
            if (!yamlFile.exists()) {
                yamlFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(yamlFile);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
        Yaml yaml = new Yaml();
        yaml.dump(values, writer);
    }
}
