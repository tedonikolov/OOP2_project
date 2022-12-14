package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.services.QueriesDistributorService;
import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.EventDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bg.tu_varna.sit.oop2_project.busnessLayer.EventTable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class QueriesDistributorController implements Initializable {
    private Stage stage;
    private double stageWight;
    private double stageHigh;
    @FXML
    private Button back;
    @FXML
    private TableView<EventDTO> event;
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker to;
    @FXML
    private TextField address;
    @FXML
    private ComboBox eventBox;

    public void back(ActionEvent event){
        SceneChanger.change(event,"distributor.fxml");
    }

    public void back2(){
        event.setVisible(false);
        back.setVisible(false);
        stage.setWidth(stageWight);
        stage.setHeight(stageHigh);
    }

    public void clear(){
        address.clear();
        to.setValue(null);
        from.setValue(null);
        eventBox.setValue(null);
    }

    public void events(ActionEvent actionEvent) throws SQLException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageHigh = stage.getHeight();
        stageWight = stage.getWidth();
        stage.setWidth(event.getWidth()+10);
        stage.setHeight(490);

        event.getItems().clear();

        QueriesDistributorService.showEvents(event,eventBox,from,to,address.getText());

        event.setVisible(true);
        back.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        QueriesDistributorService.init(eventBox);
        eventBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(eventBox.getValue()!=null){
                    address.clear();
                    to.setDisable(true);
                    to.setValue(null);
                    from.setDisable(true);
                    from.setValue(null);
                    address.setDisable(true);
                }
                else {
                    from.setDisable(false);
                    to.setDisable(false);
                    address.setDisable(false);
                }
            }
        });
    }
}
