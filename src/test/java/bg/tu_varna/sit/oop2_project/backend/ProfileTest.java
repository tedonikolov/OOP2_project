package bg.tu_varna.sit.oop2_project.backend;

import bg.tu_varna.sit.oop2_project.entities.Profiles;
import bg.tu_varna.sit.oop2_project.entities.Roles;
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