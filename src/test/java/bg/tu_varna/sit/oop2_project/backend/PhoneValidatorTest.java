package bg.tu_varna.sit.oop2_project.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneValidatorTest {

    @Test
    void isPhoneNumberCorrect() {
        assertTrue(PhoneValidator.validate("0884984898"));
        assertTrue(PhoneValidator.validate("+359884984898"));
        assertFalse(PhoneValidator.validate("0784756585"));
        assertFalse(PhoneValidator.validate("+35784756585"));
        assertFalse(PhoneValidator.validate("+359784756585"));
        assertFalse(PhoneValidator.validate("359884986585"));
        assertFalse(PhoneValidator.validate("478758"));
    }
}