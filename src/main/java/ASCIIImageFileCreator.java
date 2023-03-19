
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class ASCIIImageFileCreator {

    private FileWriter writer;
    private int style;
    private char alghoritm;
    private String imgName;
    private boolean inverted;

    public ASCIIImageFileCreator(String imgName, char alghoritm, int style, boolean inverted) throws IOException {
        this.imgName = imgName;
        this.alghoritm = alghoritm;
        this.inverted = inverted;
        this.style = style;
        writer = new FileWriter(ASCIIArt.PATH + "\\outputs\\"
                + outputFileName(), Charset.forName("UTF-8"));
    }

    public void writeFile(String content) throws IOException {
        writer.write(content);
        writer.flush();
    }

    public String outputFileName() {
        StringBuilder outputFile = new StringBuilder(imgName);
        outputFile.delete(outputFile.lastIndexOf("."), outputFile.length());
        outputFile.append("_a");
        outputFile.append(alghoritm);
        outputFile.append("_s");
        outputFile.append(style);
        if(inverted) {
            outputFile.append("_INV");
        }
        outputFile.append(".txt");
        return outputFile.toString();
    }

}
