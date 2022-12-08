package bg.tu_varna.sit.oop2_project.backend;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    public void testConnection(){
        Database.connection();
        assertNotNull(Database.connection());
        Database.close();
    }

    @Test
    public void isClosed(){
        assertThrows(NullPointerException.class,()->Database.close());
    }
}