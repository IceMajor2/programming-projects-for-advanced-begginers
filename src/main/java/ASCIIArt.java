
import java.io.IOException;

public class ASCIIArt {
    
    /*
    TODO:
            ASCII looks a bit stretched out - work on the width
     */
    
    public static final char[][] STYLES = ProgramLogic.getStyles();
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
