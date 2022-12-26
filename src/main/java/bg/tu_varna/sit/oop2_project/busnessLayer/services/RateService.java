package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.DistributorRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import javafx.scene.control.ComboBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class RateService {
    public static void init(ComboBox comboBox){
        Set<String> distributor=new HashSet<>();
        List<Distributor> distributors= DistributorRepository.get();
        for(Tickets ticket : TicketsRepository.get()) {
            for (Distributor distributor1 : distributors) {
                if (ticket.getSectors().getEvent().getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile() && ticket.getDistributor().getIdProfile() == distributor1.getIdProfile()) {
                    String fullName = distributor1.getFirstName() + " " + distributor1.getLastName();
                    distributor.add(fullName);
                }
            }
        }
        comboBox.getItems().addAll(distributor);
    }

    public static boolean rate(String fullName,int rate ) {
        List<Distributor> distributors = DistributorRepository.get();
        List<Tickets> ticketsList = TicketsRepository.get();
        String string = fullName;
        String[] name = string.split(" ");

        for (Distributor distributor : distributors) {
            if (Objects.equals(distributor.getFirstName(), name[0]) && Objects.equals(distributor.getLastName(), name[1])) {
                for (Tickets tickets : ticketsList) {
                    if (tickets.getDistributor().getIdProfile() == distributor.getIdProfile()) {
                        if (tickets.getRate() == 0) {
                            double rating = distributor.getRating();
                            int curr = rate;

                            if (rating == 0)
                                rating = curr;
                            else {
                                rating += curr;
                                rating = rating / 2;
                                DecimalFormat df = new DecimalFormat("0.00");
                                try {
                                    rating = (Double) df.parse(df.format(rating));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            TicketsRepository.updateRate(String.valueOf(curr), distributor.getIdProfile());

                            DistributorRepository.updateRating(String.valueOf(rating), distributor.getIdProfile());

                            LogManager.shutdown();
                            System.setProperty("logFilename", "info.log");
                            Logger logger = LogManager.getLogger();
                            logger.info("Distributor rated successful! profile ID:" + distributor.getIdProfile());
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}
