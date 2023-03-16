
import java.io.IOException;

public class ASCIIArt {
    
    /*
    TODO:
          create Exceptions
          fileName consists invertStatus
		  ASCII looks a bit stretched out - work on the width
		  provide a suggestion system for an optimal scaling value
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
