package photomosaics;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("===Photomosaics===");
        System.out.println("");
        while (true) {
            System.out.println("1. Make photomosaic");
            System.out.println("2. Crop (and load) dataset");
            System.out.println("0. Exit");
            System.out.print("> ");
            String choice = scanner.nextLine();
            if ("0".equals(choice)) {
                break;
            }
            if ("1".equals(choice)) {
                makePhotomosaic();
                continue;
            }
            if("2".equals(choice)) {
                cropSources();
                continue;
            }
        }
    }
    
    public void cropSources() {
        System.out.println("Processing...");
        try {
            ImageHandler.cropDataset();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Success!");
    }

    public void makePhotomosaic() {
        System.out.println("===");

        for (File file : DataHandler.inputs()) {
            System.out.println(file.getName());
        }

        System.out.println("===");
        System.out.print("Choose image: ");

        while (true) {

            File imageFile = new File(DataHandler.PATH_TO_INPUT + scanner.nextLine());
            if (!imageFile.exists()) {
                System.out.println("Couldn't find image. (Remember about file extension)");
                continue;
            }

            String format = imageFile.getName().substring(
                    imageFile.getName().lastIndexOf('.') + 1);
            String nameWithoutFormat = imageFile.getName().substring(0,
                    imageFile.getName().lastIndexOf('.'));
            var img = DataHandler.readImg(imageFile);

            System.out.println("Give size of a \"photo pixel\" size. (Leave empty for automatic estimate)");
            System.out.print("> ");
            String input = scanner.nextLine();

            BufferedImage mosaic = null;
            System.out.println("Creating photomosaic...");
            if (!input.isEmpty()) {
                int groupDim = Integer.valueOf(scanner.nextLine());
                mosaic = ImageHandler.photomosaic(img, groupDim);
            } else {
                mosaic = ImageHandler.photomosaic(img);
            }

            try {
                DataHandler.outputImg(mosaic, String.format("%s_pm.%s",
                        nameWithoutFormat, format), format);
                System.out.println("Success!"
                        + " See the output in the \"output\" folder.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            break;
        }
    }

}
