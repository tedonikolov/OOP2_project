package bg.tu_varna.sit.oop2_project.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
    public static String hashing(String pass) throws NoSuchAlgorithmException {
        //hashing the password
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Password hashed correctly");
        return sb.toString();
    }
}
