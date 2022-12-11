package bg.tu_varna.sit.oop2_project.busnessLayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {
    public static boolean validate(String phone) {
        String patterns= "^0(87|88|89)[0-9]{7}$|^\\+359(87|88|89)[0-9]{7}$";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
