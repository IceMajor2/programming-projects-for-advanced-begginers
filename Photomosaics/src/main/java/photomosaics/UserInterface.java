package photomosaics;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

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
        }
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
            try {
                var mosaic = ImageHandler.photomosaic(img);
                DataHandler.outputImg(mosaic, String.format("%s_pm.%s",
                        nameWithoutFormat, format), format);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            break;
        }
    }

}
