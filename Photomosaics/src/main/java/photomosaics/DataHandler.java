package photomosaics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class DataHandler {

    public static Map<File, int[]> IMGS_COLORS = null;
    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);
    public static String PATH_TO_DATASET = String.format("%s%s%s%s", "pictures",
            File.separator, "dataset", File.separator);
    public static String DIRECT_PATH_TO_VALUES = String.format("%s%s%s%s%s%s%s",
            "src", File.separator, "main", File.separator, "resources",
            File.separator, "dataset_RGB.yml");

    public static void outputImg(BufferedImage img, String imgName, String format) throws IOException {
        try {
            Files.createDirectory(Path.of(PATH_TO_OUTPUT));
        } catch (FileAlreadyExistsException e) {}
        ImageIO.write(img, format, new File(PATH_TO_OUTPUT + imgName));
    }

    public static void outputImg(BufferedImage img, String imgName) throws IOException {
        outputImg(img, imgName, "jpg");
    }

    public static BufferedImage readImg(File imgFile) {
        try {
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage copy(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public static File[] filesIn(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return pathname.isFile();
            }
        });
        return files;
    }

    public static File[] dataset() {
        return filesIn(PATH_TO_DATASET + "cropped" + File.separator);
    }

    public static File[] inputs() {
        return filesIn(PATH_TO_INPUT);
    }

    public static Map<File, int[]> loadValuesFromDir() {
        Map<File, int[]> colorValues = new HashMap<>();
        File[] imgs = dataset();

        for (File imgFile : imgs) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(imgFile);
            } catch (IOException e) {
                System.out.println(e);
            }
            int groupDim = img.getHeight();
            int[] colorVal = ImageHandler.pixelGroup(img, groupDim)[0][0];

            colorValues.put(imgFile, colorVal);
        }

        return colorValues;
    }

    public static boolean isDatasetUpToDate(Map<File, int[]> yaml) {
        if (yaml == null) {
            return false;
        }
        HashSet<File> filesYaml = new HashSet<>(yaml.keySet().stream()
                .collect(Collectors.toList()));
        HashSet<File> filesDir = new HashSet<>(List.of(dataset()));

        return filesYaml.equals(filesDir);
    }

    public static void load() {
        Map<File, int[]> filesYaml = readYaml();
        if (!isDatasetUpToDate(filesYaml)) {
            System.out.println("Database was not up-to-date.\nLoading files...");
            filesYaml = loadValuesFromDir();
            System.out.println("Files loaded successfully.");
        }
        IMGS_COLORS = filesYaml;
        writeYaml(IMGS_COLORS);
    }

    public static Map<File, int[]> readYaml() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(DIRECT_PATH_TO_VALUES));
        } catch (FileNotFoundException e) {
            return null;
        }

        Yaml yaml = new Yaml();

        Map<String, Object> data = yaml.load(inputStream);
        if (data == null) {
            return null;
        }
        Map<File, int[]> output = new HashMap<>();

        for (var entry : data.entrySet()) {
            String key = entry.getKey();
            var value = (ArrayList) entry.getValue();

            int[] rgbVals = new int[3];

            rgbVals[0] = (int) value.get(0);
            rgbVals[1] = (int) value.get(1);
            rgbVals[2] = (int) value.get(2);
            File file = new File(PATH_TO_DATASET + "cropped" + File.separator + key);
            output.put(file, rgbVals);
        }
        return output;
    }

    public static void writeYaml(Map<File, int[]> values) {
        File yamlFile = new File(DIRECT_PATH_TO_VALUES);
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
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        Yaml yaml = new Yaml();
        for (var entry : values.entrySet()) {
            String fileName = entry.getKey().getName();
            int[] rgbArr = entry.getValue();
            Map<String, int[]> map = Map.of(fileName, rgbArr);
            yaml.dump(map, writer);
        }
    }

    public static String getDotlessExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.') + 1);
    }

    public static String getNameMinusExtension(File file) {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }
}
