package bg.tu_varna.sit.oop2_project.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    @Test
    void isEmailCorrect(){
        assertTrue(EmailValidator.validate("tedo@abv.bg"));
        assertTrue(EmailValidator.validate("Ivan01@gmail.com"));
        assertFalse(EmailValidator.validate("tedoabv.bg"));
        assertFalse(EmailValidator.validate("tedoabvbg"));
        assertFalse(EmailValidator.validate("tedo@abvbg"));
        assertTrue(EmailValidator.validate("s20621525@onlineedu.tu-varna.bg"));
    }
}