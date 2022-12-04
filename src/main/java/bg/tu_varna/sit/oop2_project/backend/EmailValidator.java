package bg.tu_varna.sit.oop2_project.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean validate(String email) {
        String patterns= "^(\\S+)@(\\S+)\\.(\\S+)$";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
