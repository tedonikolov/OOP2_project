package bg.tu_varna.sit.oop2_project.busnessLayer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class PasswordHashTest {

    @Test
    public void shouldHashPassword() throws NoSuchAlgorithmException {
        String pass = PasswordHash.hashing("test");
        Assertions.assertFalse(pass.isEmpty());
        Assertions.assertNotEquals(pass,"test");
    }

    @Test
    public void notnull(){
        Assertions.assertThrows(RuntimeException.class,()->{
            PasswordHash.hashing(null);
        });
    }
}

