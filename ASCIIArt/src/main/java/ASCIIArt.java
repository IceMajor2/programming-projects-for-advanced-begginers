
import java.io.File;
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
        String targetClassesDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File rootDir = new File(targetClassesDir).getParentFile().getParentFile();
        String path = String.format("%s%s", rootDir.getAbsolutePath(), File.separator);
        return path;
    }
}
