package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.DistributorRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SectorsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Distributor;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static eu.hansolo.tilesfx.Tile.GREEN;
import static eu.hansolo.tilesfx.Tile.RED;

public class TicketController implements Initializable{
    private List<Tickets> tickets;
    private List<Distributor> distributors;
    @FXML
    private ChoiceBox distributor;
    @FXML
    private ChoiceBox event;
    @FXML
    private Label number;
    @FXML
    private Label salary1;
    @FXML
    private Label complete;

    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
    }

    public void addDistributor(){
        if (event.getValue() != null && distributor.getValue()!=null) {

            List<Sectors> sectorsList = SectorsRepository.get();

            for (Distributor distributor : distributors) {
                String string = this.distributor.getValue().toString();
                String[] name = string.split(" ");
                if (Objects.equals(distributor.getFirstName(), name[0]) && Objects.equals(distributor.getFirstName(), name[0])) {
                    for (Sectors sectors : sectorsList) {
                        if (Objects.equals(sectors.getEvent().getName(), this.event.getValue().toString())) {

                            int id = TicketsRepository.autonumber();
                            Tickets ticket = new Tickets(id, sectors, 0, distributor, 0);
                            TicketsRepository.add(ticket);

                            LogManager.shutdown();
                            System.setProperty("logFilename", "info.log");
                            Logger logger = LogManager.getLogger();
                            logger.info("Distributor added successful: " + distributor.getIdProfile() + "," + distributor.getFirstName() + "," + distributor.getLastName());

                            LogManager.shutdown();
                            System.setProperty("logFilename", "distributor_id_" + distributor.getIdProfile() + ".log");
                            logger = LogManager.getLogger();
                            logger.info(sectors.getEvent().getName());
                        }
                    }
                }
            }
            complete.setVisible(true);
            complete.setTextFill(GREEN);
            complete.setText("Успешно назначихте разпространителя");
        }else {
            complete.setVisible(true);
            complete.setTextFill(RED);
            complete.setText("Изберете събитие и разпространител");
        }
    }

    public void show() {
        if(this.distributor.getValue()!=null) {
            String string = this.distributor.getValue().toString();
            String[] name = string.split(" ");
            for (Distributor distributor1 : distributors) {
                if (Objects.equals(distributor1.getFirstName(), name[0]) && Objects.equals(distributor1.getFirstName(), name[0])) {
                    salary1.setText(Double.toString(distributor1.getSalary()));
                    salary1.setVisible(true);
                    number.setText(Double.toString(distributor1.getRating()));
                    number.setVisible(true);
                    break;
                }
            }
        }
    }

    public void distributors() {
        distributor.getItems().clear();
        List<String> distributors=new ArrayList<>();
        for(Distributor distributor: this.distributors){
            boolean flag=true;
            for(Tickets tickets : tickets){
                if(Objects.equals(tickets.getSectors().getEvent().getName(), event.getValue().toString())&&Objects.equals(tickets.getDistributor().getIdProfile(), distributor.getIdProfile())){
                    flag=false;
                    break;
                }
            }
            if (flag) {
                String fullName=distributor.getFirstName()+" "+ distributor.getLastName();
                distributors.add(fullName);
            }
        }
        distributor.getItems().addAll(distributors);
        number.setVisible(false);
        salary1.setVisible(false);
        complete.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> events = new ArrayList<>();
        for (Event event : EventRepository.get()) {
            if (event.getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile())
                events.add(event.getName());
        }
        event.getItems().addAll(events);
        distributors = DistributorRepository.get();

        event.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (event.getValue() != null) {
                    tickets = TicketsRepository.get();
                    distributors();
                }
            }
        });

        distributor.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (event.getValue() != null) {
                    show();
                }
            }
        });
    }
}
