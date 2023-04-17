package photomosaics;

import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Map;
import java.util.HashMap;
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
    public static Map<String, int[]> IMGS_COLORS = loadColorValues();

    public static void main(String[] args) throws IOException {
        for (var entry : IMGS_COLORS.entrySet()) {
            int[] value = entry.getValue();
            System.out.println(entry.getKey() + " -> " + value[0] + " " + value[1]
                    + " " + value[2]);
        }
    }

    public static void outputImg(BufferedImage img, String imgName, String format) throws IOException {
        ImageIO.write(img, format, new File(PATH_TO_OUTPUT + imgName));
    }

    public static void outputImg(BufferedImage img, String imgName) throws IOException {
        outputImg(img, imgName, "jpg");
    }

    public static int calculatePxGroupDimension(BufferedImage img) {
        int picHeight = img.getHeight();
        int picWidth = img.getWidth();

        int minTotalR = 100;
        int equivalentLength = 44;
        for (int length = 45; length <= 55; length++) {
            int heightR = picHeight % length;
            int widthR = picWidth % length;
            int totalR = heightR + widthR;

            if (minTotalR > totalR) {
                minTotalR = totalR;
                equivalentLength = length;
            }
        }
        return equivalentLength;
    }

    public static int[][][] pixelGroup(BufferedImage img, int groupDim) {
        int picWidth = img.getWidth();
        int picHeight = img.getHeight();

        double rows = img.getHeight() / (double) groupDim;
        double columns = img.getWidth() / (double) groupDim;
        BigDecimal rowsBd = BigDecimal.valueOf(rows);
        BigDecimal columnsBd = BigDecimal.valueOf(columns);
        rowsBd = rowsBd.setScale(0, RoundingMode.CEILING);
        columnsBd = columnsBd.setScale(0, RoundingMode.CEILING);
        rows = rowsBd.doubleValue();
        columns = columnsBd.doubleValue();
        int[][][] allGroups = new int[(int) rows][(int) columns][3];

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                int gColumns = groupDim;
                int gRows = groupDim;
                if (column == columns - 1) {
                    gColumns = picWidth - (column * groupDim);
                }
                if (row == rows - 1) {
                    gRows = picHeight - (row * groupDim);
                }
                int[][] group = new int[gRows][gColumns];
                for (int x = column * groupDim; x < column * groupDim + gColumns
                        && x < picWidth; x++) {

                    for (int y = row * groupDim; y < row * groupDim + gRows
                            && y < picHeight; y++) {
                        group[y % groupDim][x % groupDim] = img.getRGB(x, y);
                    }
                }
                int[] avgRGB = calculateAverageColor(group);
                int red = avgRGB[0];
                int green = avgRGB[1];
                int blue = avgRGB[2];

                allGroups[row][column][0] = red;
                allGroups[row][column][1] = green;
                allGroups[row][column][2] = blue;
            }
        }
        return allGroups;
    }

    public static int[] calculateAverageColor(int[][] pixelGroup) {
        double redTotal = 0;
        double greenTotal = 0;
        double blueTotal = 0;

        int iterations = 0;
        for (int i = 0; i < pixelGroup.length; i++) {
            for (int y = 0; y < pixelGroup[i].length; y++) {
                Color c = new Color(pixelGroup[i][y]);
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                redTotal += red;
                greenTotal += green;
                blueTotal += blue;
                iterations++;
            }
        }

        int redAvg = (int) redTotal / iterations;
        int greenAvg = (int) greenTotal / iterations;
        int blueAvg = (int) blueTotal / iterations;

        return new int[]{redAvg, greenAvg, blueAvg};
    }

    public static BufferedImage pixelate(BufferedImage img, int[][][] pxGroup, int groupDim) {
        BufferedImage copy = copy(img);
        Graphics2D graph = copy.createGraphics();
        for (int row = 0; row < pxGroup.length; row++) {

            for (int column = 0; column < pxGroup[0].length; column++) {
                int red = (int) pxGroup[row][column][0];
                int green = (int) pxGroup[row][column][1];
                int blue = (int) pxGroup[row][column][2];
                graph.setColor(new Color(red, green, blue));
                graph.fill(new Rectangle(column * groupDim, row * groupDim, groupDim, groupDim));

            }
        }
        graph.dispose();
        return copy;
    }

    public static BufferedImage copy(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public static void cropDataset() throws IOException {
        File dir = new File(PATH_TO_DATASET);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName().toLowerCase();
                return pathname.isFile();
            }
        });
        for (File file : files) {
            BufferedImage img = ImageIO.read(file);
            var cropped = cropToSquare(img);
            ImageIO.write(cropped, "jpg", new File(PATH_TO_DATASET + File.separator
                    + "cropped" + File.separator + file.getName()));
        }
    }

    public static BufferedImage cropToSquare(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        int smaller = height > width ? width : height;
        var newImg = Scalr.resize(img, Scalr.Mode.FIT_EXACT, smaller, smaller);
        return newImg;
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
            int[] colorVal = pixelGroup(img, groupDim)[0][0];

            colorValues.put(imgFile.getName(), colorVal);
        }

        return colorValues;
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

}
