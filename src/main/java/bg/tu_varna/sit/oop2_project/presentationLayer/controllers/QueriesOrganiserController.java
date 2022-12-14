package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.collections.GetEvents;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.DistributorDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.EventDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class QueriesOrganiserController implements Initializable {
    private Stage stage;
    private double stageWight;
    private double stageHigh;
    @FXML
    private Button back;
    @FXML
    private TableView<EventDTO> event;
    @FXML
    private TableView<DistributorDTO> distributor;
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker to;
    @FXML
    private TextField address;
    @FXML
    private TextField from2;
    @FXML
    private TextField to2;
    @FXML
    private ComboBox eventBox;
    @FXML
    private ComboBox eventBox1;



    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
    }

    public void back2(){
        event.setVisible(false);
        distributor.setVisible(false);
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
        stage.setWidth(event.getWidth());
        stage.setHeight(490);

        event.getItems().clear();
        distributor.setVisible(false);

        EventTable.table(event);
        Connection connection = Database.connection();
        PreparedStatement statement;
        if (eventBox.getValue() != null) {
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE NAME='" + eventBox.getValue().toString() + "' AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
        } else if (from.getValue() != null && to.getValue() != null) {
            if (Objects.equals(this.address.getText(), "")) {
                LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
                LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
                statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            } else {
                LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
                LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
                statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE DATETIME BETWEEN to_date('" + fromDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND to_date('" + toDate + "','YYYY-MM-DD\"T\"HH24:MI:SS') AND ADDRESS LIKE '%" + this.address.getText() + "%' AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            }
        } else if (!Objects.equals(this.address.getText(), "")) {
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE ADDRESS LIKE '%" + this.address.getText() + "%' AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
        } else
            statement = connection.prepareStatement("SELECT NAME, ADDRESS, to_char(DATETIME, 'yyyy-mm-dd') datepart, to_char(DATETIME, 'hh24:mi:ss') timepart, TYPE, AMOUNT, SOLD, PRICE  FROM SECTORS JOIN SEATS S on SECTORS.SEATS_ID = S.ID_SEATS JOIN EVENT E on E.ID_EVENT = SECTORS.EVENT_ID WHERE ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            EventDTO eventDTO = new EventDTO(result.getString(1), result.getString(2), LocalDate.parse(result.getString(3), formatter), LocalTime.parse(result.getString(4)), result.getString(5), Integer.parseInt(result.getString(6)), Integer.parseInt(result.getString(7)), Double.parseDouble(result.getString(8)));
            event.getItems().add(eventDTO);
        }
        event.setVisible(true);
        back.setVisible(true);
        result.getStatement().close();
        result.close();
    }



    public void distributors(ActionEvent actionEvent) throws SQLException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageHigh = stage.getHeight();
        stageWight = stage.getWidth();
        stage.setWidth(distributor.getWidth());
        stage.setHeight(490);

        distributor.getItems().clear();
        event.setVisible(false);

        TableColumn name = new TableColumn<DistributorDTO, String>("Разпространител");
        name.setCellValueFactory(new PropertyValueFactory<DistributorDTO, String>("name"));

        TableColumn email = new TableColumn<DistributorDTO, String>("Имейл");
        email.setCellValueFactory(new PropertyValueFactory<DistributorDTO, String>("email"));

        TableColumn phone = new TableColumn<DistributorDTO, String>("Телефон");
        phone.setCellValueFactory(new PropertyValueFactory<DistributorDTO, String>("phone"));

        TableColumn rating = new TableColumn<DistributorDTO, String>("Рейтинг");
        rating.setCellValueFactory(new PropertyValueFactory<DistributorDTO, Integer>("rating"));

        TableColumn salary = new TableColumn<DistributorDTO, String>("Заплата");
        salary.setCellValueFactory(new PropertyValueFactory<DistributorDTO, Integer>("salary"));

        TableColumn event = new TableColumn<DistributorDTO, String>("Евент");
        event.setCellValueFactory(new PropertyValueFactory<DistributorDTO, Integer>("event"));

        TableColumn seat = new TableColumn<DistributorDTO, String>("Място");
        seat.setCellValueFactory(new PropertyValueFactory<DistributorDTO, Integer>("seat"));

        TableColumn sold = new TableColumn<DistributorDTO, String>("Продажби");
        sold.setCellValueFactory(new PropertyValueFactory<DistributorDTO, Integer>("sold"));

        distributor.getColumns().setAll(name,email,phone,rating,salary,event,seat,sold);

        Connection connection = Database.connection();
        PreparedStatement statement;

        if(eventBox1.getValue()!=null){
            if(!Objects.equals(from2.getText(), "") && !Objects.equals(to2.getText(), "")){
                statement=connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD BETWEEN "+Integer.parseInt(from2.getText())+" AND "+Integer.parseInt(to2.getText())+" AND NAME='"+eventBox1.getValue().toString()+"' AND ORGANISER_ID="+Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(from2.getText(), "")) {
                statement=connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD>="+Integer.parseInt(from2.getText())+" AND NAME='"+eventBox1.getValue().toString()+"' AND ORGANISER_ID="+Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(to2.getText(), "")) {
                statement=connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD<="+Integer.parseInt(to2.getText())+" AND NAME='"+eventBox1.getValue().toString()+"' AND ORGANISER_ID="+Profile.getProfiles().getIdProfile());
            }
            else {
                statement=connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE NAME='"+eventBox1.getValue().toString()+"' AND ORGANISER_ID="+Profile.getProfiles().getIdProfile());
            }
        }
        else {
            if (!Objects.equals(from2.getText(), "") && !Objects.equals(to2.getText(), "")) {
                statement = connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD BETWEEN " + Integer.parseInt(from2.getText()) + " AND " + Integer.parseInt(to2.getText()) + " AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(from2.getText(), "")) {
                statement = connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD>=" + Integer.parseInt(from2.getText()) + " AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(to2.getText(), "")) {
                statement = connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE TICKETSOLD<=" + Integer.parseInt(to2.getText()) + " AND ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            } else {
                statement = connection.prepareStatement("SELECT FIRSTNAME, LASTNAME, EMAIL, PHONE, RATING, SALARY, NAME, TYPE, TICKETSOLD FROM TICKETS JOIN DISTRIBUTOR D on D.ID_PROFILE = TICKETS.DISTRIBUTOR_ID JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID JOIN EVENT E on E.ID_EVENT = S.EVENT_ID JOIN SEATS S2 on S2.ID_SEATS = S.SEATS_ID WHERE ORGANISER_ID=" + Profile.getProfiles().getIdProfile());
            }
        }
        ResultSet result = statement.executeQuery();
        while (result.next()){
            DistributorDTO distributorDTO =new DistributorDTO(result.getString(1)+" "+result.getString(2),result.getString(3),result.getString(4),Double.parseDouble(result.getString(5)),Double.parseDouble(result.getString(6)),result.getString(7),result.getString(8),Integer.parseInt(result.getString(9)));
            distributor.getItems().add(distributorDTO);
        }
        distributor.setVisible(true);
        result.getStatement().close();
        result.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> events=new ArrayList<>();
        events.add(null);
        for(Event event: GetEvents.get()){
            if(event.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(event.getName());
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
        eventBox1.getItems().addAll(events);
    }
}
