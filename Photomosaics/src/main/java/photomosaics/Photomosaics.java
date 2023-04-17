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

public class Photomosaics {

    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);
    public static String PATH_TO_DATASET = String.format("%s%s%s%s", "pictures",
            File.separator, "dataset", File.separator);

    public static void main(String[] args) throws IOException {
        cropDataset();
        
        BufferedImage picSource = ImageIO.read(new File(PATH_TO_INPUT + "sf.jpg"));
        int groupDim = calculatePxGroupDimension(picSource);
        //int groupDim = 5;
        int[][][] avgs = pixelGroupsColorAvg(picSource, groupDim);
        BufferedImage pixelated = pixelate(picSource, avgs, groupDim);
        ImageIO.write(pixelated, "jpg", new File(PATH_TO_OUTPUT + "pixelated.jpg"));
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

    public static int[][][] pixelGroupsColorAvg(BufferedImage img, int groupDim) {
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
                int[] avgRGB = colorAvgOfPixelGroup(group);
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

    public static int[] colorAvgOfPixelGroup(int[][] pixelGroup) {
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
}
