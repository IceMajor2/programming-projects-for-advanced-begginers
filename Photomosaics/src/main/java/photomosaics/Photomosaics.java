package photomosaics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

public class Photomosaics {

    public static String PATH_TO_INPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "input", File.separator);
    public static String PATH_TO_OUTPUT = String.format("%s%s%s%s", "pictures",
            File.separator, "output", File.separator);

    public static void main(String[] args) throws IOException {
        BufferedImage picSource = ImageIO.read(new File(PATH_TO_INPUT + "nature.jpg"));
        double[][][] avgs = getPixelGroupsColor(picSource,
                determinePixelGroupSideLength(picSource));
    }

    private static int determinePixelGroupSideLength(BufferedImage img) {
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

    public static double[][][] getPixelGroupsColor(BufferedImage img, int groupDim) {
        int rows = img.getHeight() / groupDim;
        int columns = img.getWidth() / groupDim;
        double[][][] groups = new double[rows][columns][3];
        
        int row = 0;
        while (row < rows) {
            for (int column = 0; column < columns; column++) {
                
                double avgRed = 0;
                double avgGreen = 0;
                double avgBlue = 0;

                for (int x = column * groupDim; x < column * groupDim + groupDim; x++) {

                    for (int y = row * groupDim; y < row * groupDim + groupDim; y++) {

                        if(row == rows - 1) {
                            
                        }
                        
                        int pixel = img.getRGB(x, y);
                        Color c = new Color(pixel);
                        int red = c.getRed();
                        int green = c.getGreen();
                        int blue = c.getBlue();
                        avgRed += red;
                        avgGreen += green;
                        avgBlue += blue;
                        
                    }
                }

                avgRed = avgRed / (double) (groupDim * groupDim);
                avgGreen = avgGreen / (double) (groupDim * groupDim);
                avgBlue = avgBlue / (double) (groupDim * groupDim);
                groups[row][column][0] = avgRed;
                groups[row][column][1] = avgGreen;
                groups[row][column][2] = avgBlue;
            }
            row++;
        }
        return groups;
    }
}
