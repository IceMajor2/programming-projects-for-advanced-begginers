
import java.io.IOException;
import java.io.Console;

public class ASCIIArt {

    public static final String PATH = setPath();
    public static final char[][] STYLES = ProgramLogic.getStyles();
    public static char[] CHARS = null;

    public static void main(String[] args) {
        UserInterface UI = new UserInterface();
        try {
            UI.start();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static String setPath() {
        Console console = System.console();
        String separator = java.io.File.separator;
        String path = console == null ? "" : String.format("..%s..%s..%s",
                separator, separator, separator);
        return path;
    }
}
