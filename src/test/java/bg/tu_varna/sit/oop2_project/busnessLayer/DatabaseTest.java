package bg.tu_varna.sit.oop2_project.busnessLayer;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    public void testConnection(){
        Database.connection();
        assertNotNull(Database.connection());
    }

}

