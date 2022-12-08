package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.SceneChanger;
import bg.tu_varna.sit.oop2_project.backend.collections.GetTickets;
import bg.tu_varna.sit.oop2_project.entities.Tickets;
import bg.tu_varna.sit.oop2_project.backend.DTO.EventDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bg.tu_varna.sit.oop2_project.backend.EventTable;

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
        if(Database.connection()!=null)
            Database.close();
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

        Connection connection=Database.connection();
        PreparedStatement statement;
        EventTable.table(event);
        if (eventBox.getValue() != null) {
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE NAME='" + eventBox.getValue().toString() + "' AND DISTRIBUTOR_ID=" + Profile.getProfiles().getIdProfile());
        } else if (from.getValue() != null && to.getValue() != null) {
            if (Objects.equals(this.address.getText(), "")) {
                LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
                LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
                statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND DISTRIBUTOR_ID=" + Profile.getProfiles().getIdProfile());
            } else {
                LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
                LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
                statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ADDRESS LIKE '%" + this.address.getText() + "%' AND DISTRIBUTOR_ID=" + Profile.getProfiles().getIdProfile());
            }
        } else if (!Objects.equals(this.address.getText(), "")) {
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE ADDRESS LIKE '%" + this.address.getText() + "%' AND DISTRIBUTOR_ID=" + Profile.getProfiles().getIdProfile());
        } else
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, TICKETSOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID JOIN TICKETS T on SECTORS.ID_SECTORS = T.SECTORS_ID WHERE DISTRIBUTOR_ID=" + Profile.getProfiles().getIdProfile());
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            EventDTO eventDTO = new EventDTO(result.getString(1), result.getString(2), LocalDate.parse(result.getString(3), formatter), LocalTime.parse(result.getString(4)), result.getString(5), Integer.parseInt(result.getString(6)), Integer.parseInt(result.getString(7)), Double.parseDouble(result.getString(8)));
            event.getItems().add(eventDTO);
        }
        event.setVisible(true);
        back.setVisible(true);
        Database.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Set<String> events=new HashSet<>();
        events.add(null);
        for(Tickets tickets: GetTickets.get()){
            if(tickets.getDistributor().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(tickets.getSectors().getEvent().getName());
        }
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
        eventBox.getItems().addAll(events);
    }
}
