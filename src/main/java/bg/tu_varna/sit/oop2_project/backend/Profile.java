package bg.tu_varna.sit.oop2_project.backend;

import bg.tu_varna.sit.oop2_project.entities.Profiles;

public class Profile {
    private static Profiles profiles;

    public static void setProfiles(Profiles profiles){
        Profile.profiles=profiles;
    }

    public static Profiles getProfiles(){
        return profiles;
    }
}
