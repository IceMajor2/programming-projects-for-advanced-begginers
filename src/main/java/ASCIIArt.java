
import java.io.IOException;

public class ASCIIArt {
    
    /*
    TODO: fix Java decimals error caused by multiplication in PixelConverter
          create UserInterface class
          create Exceptions
          fileName consists invertStatus
     */
    
    public static final char[][] STYLES = UserInterface.getStyles();
    public static final String PATH
            = "C:\\Users\\IceMajor\\Documents\\NetBeansProjects\\ASCIIArt\\";
    public static char[] CHARS = null;

    public static void main(String[] args) {
        UserInterface UI = new UserInterface();
        try {
            UI.start();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
