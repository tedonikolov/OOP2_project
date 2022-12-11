package bg.tu_varna.sit.oop2_project.busnessLayer;

import bg.tu_varna.sit.oop2_project.dataLayer.entities.Profiles;

public class Profile {
    private static Profiles profiles;

    public static void setProfiles(Profiles profiles){
        Profile.profiles=profiles;
    }

    public static Profiles getProfiles(){
        return profiles;
    }
}
