package userlogins.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import org.yaml.snakeyaml.Yaml;

public class AppLogic {
    
    public static String databaseFileName() {
        try {
            InputStream inputStream = new FileInputStream(new File("database_info.yaml"));
            Yaml yaml = new Yaml();
            LinkedHashMap info = yaml.load(inputStream);
            return (String) info.get("db_name");
        } catch(FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public static List<LinkedHashMap> loadConfig() {
        try {
            InputStream inputStream = new FileInputStream(new File("config.yaml"));
            Yaml yaml = new Yaml();
            var credentials = (List<LinkedHashMap>) yaml.load(inputStream);
            return credentials;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public static byte[] bytesSHA256(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            return md5.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA256(String password) {
        byte[] bytesSHA = bytesSHA256(password);
        BigInteger number = new BigInteger(1, bytesSHA);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static boolean credentialsValidConfig(List<LinkedHashMap> cfgDatabase, String usr, String pass) {
        String hashedPass = getSHA256(pass);
        for (LinkedHashMap usrData : cfgDatabase) {
            if (usrData.get("username").equals(usr)
                    && usrData.get("password_hash").equals(hashedPass)) {
                return true;
            }
        }
        return false;
    }
}
