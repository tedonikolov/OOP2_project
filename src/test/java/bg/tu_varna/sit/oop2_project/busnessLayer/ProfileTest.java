package bg.tu_varna.sit.oop2_project.busnessLayer;

import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Roles;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    static Profiles profiles;
    @BeforeAll
    static void setProfile(){
        profiles=new Profiles(1,"ivan","Petrov",new Roles(1,"admin"));
        Profile.setProfiles(profiles);
    }

    @Test
    void profileShouldNotBeNull(){
        assertNotNull(Profile.getProfiles());
    }

    @Test
    void profileShouldBeTheSame(){
        assertSame(Profile.getProfiles(), profiles);
    }
}

