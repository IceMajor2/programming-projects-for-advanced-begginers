package photomosaics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import static photomosaics.DataHandler.PATH_TO_DATASET;
import static photomosaics.DataHandler.IMGS_COLORS;

public class ImageHandler {

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

    private static int[] calculateAverageColor(int[][] pixelGroup) {
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

    public static BufferedImage pixelate(BufferedImage img, int groupDim) {
        BufferedImage copy = DataHandler.copy(img);
        Graphics2D graph = copy.createGraphics();
        
        int[][][] pxGroup = pixelGroup(img, groupDim);
        
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
            cropped = Scalr.resize(cropped, 75);
            ImageIO.write(cropped, "jpg", new File(PATH_TO_DATASET + File.separator
                    + "cropped" + File.separator + file.getName()));
        }
    }

    private static BufferedImage cropToSquare(BufferedImage img) {
        int height = img.getHeight();
        int width = img.getWidth();
        int smaller = height > width ? width : height;
        var newImg = Scalr.resize(img, Scalr.Mode.FIT_EXACT, smaller, smaller);
        return newImg;
    }

    public static double distanceBetweenColors(int[] color1, int[] color2) {
        int r1 = color1[0];
        int g1 = color1[1];
        int b1 = color1[2];

        int r2 = color2[0];
        int g2 = color2[1];
        int b2 = color2[2];

        double distance = Math.pow((r2 - r1), 2) + Math.pow((g2 - g1), 2) + Math.pow((b2 - b1), 2);
        distance = Math.sqrt(distance);
        return distance;
    }

    public static BufferedImage getMostSimilarImage(int[] pixelGroupAvg) {
        String closestImg = IMGS_COLORS.entrySet().stream()
                .min((img1, img2) -> {
                    int[] rgb1 = img1.getValue();
                    int[] rgb2 = img2.getValue();
                    double diff1 = distanceBetweenColors(pixelGroupAvg, rgb1);
                    double diff2 = distanceBetweenColors(pixelGroupAvg, rgb2);
                    return Double.valueOf(diff1).compareTo(diff2);
                }).get().getKey().getName();
        File file = new File(PATH_TO_DATASET + "cropped" + File.separator + closestImg);
        try {
            return ImageIO.read(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static BufferedImage photomosaic(BufferedImage img) {
        int groupDim = calculatePxGroupDimension(img);
        return photomosaic(img, groupDim);
    }
    
    public static BufferedImage photomosaic(BufferedImage img, int groupDim) {
        BufferedImage photomosaic = DataHandler.copy(img);
        Graphics2D graph = photomosaic.createGraphics();
        
        int[][][] pixelGroups = pixelGroup(img, groupDim);
        
        int yPos = 0;
        for(int[][] row : pixelGroups) {
            
            int xPos = 0;
            for(int[] pixelGroup : row) {
                
                BufferedImage srcImg = getMostSimilarImage(pixelGroup);
                srcImg = Scalr.resize(srcImg, groupDim);
                
                graph.drawImage(srcImg, xPos, yPos, null);

                xPos += groupDim;
            }
            
            yPos += groupDim;
        }
        return photomosaic;
    }
}
