package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.busnessLayer.services.TicketService;
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
            TicketService.addDistributor(this.distributor.getValue().toString(),this.event.getValue().toString());

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
            for (Distributor distributor1 : DistributorRepository.get()) {
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
        TicketService.distributors(distributor,tickets,event.getValue().toString());
        number.setVisible(false);
        salary1.setVisible(false);
        complete.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TicketService.init(event);

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
