package photomosaics;

import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Photomosaics {

    public static File inputPic = new File("pictures" + File.separator
            + "input" + File.separator + "nature.jpg");
    public static File outputPic = new File("pictures" + File.separator
            + "output" + File.separator + "output.jpg");

    public static void main(String[] args) throws IOException {
        BufferedImage pic = ImageIO.read(inputPic);
        BufferedImage scaledPic = Scalr.resize(pic, 150);
        ImageIO.write(scaledPic, "jpg", outputPic);

    }
}
