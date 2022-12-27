package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.EventTable;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.DistributorDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.EventDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.Database;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.EventRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.QueriesRepository;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

public class QueriesOrganiserService {
    public static void init(ComboBox eventBox,ComboBox eventBox1){
        List<String> events=new ArrayList<>();
        events.add(null);
        for(Event event: EventRepository.get()){
            if(event.getOrganiser().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(event.getName());
        }
        eventBox.getItems().addAll(events);
        eventBox1.getItems().addAll(events);
    }

    public static void showEvents(TableView<EventDTO> table, ComboBox event, DatePicker from, DatePicker to, String address){
        PreparedStatement statement;
        EventTable.table(table);
        if (event.getValue() != null) {
            statement= QueriesRepository.selectEventAndOrganiser(event.getValue().toString(),Profile.getProfiles().getIdProfile());
        } else if (from.getValue() != null && to.getValue() != null) {
            LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
            LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
            if (Objects.equals(address, "")) {
                statement = QueriesRepository.selectByDateAndOrganiser(fromDate,toDate,Profile.getProfiles().getIdProfile());
            } else {
                statement = QueriesRepository.selectByDateAndAddressAndOrganiser(fromDate,toDate,address,Profile.getProfiles().getIdProfile());
            }
        } else if (!Objects.equals(address, "")) {
            statement = QueriesRepository.selectByAddressAndOrganiser(address,Profile.getProfiles().getIdProfile());
        } else
            statement = QueriesRepository.selectAllByOrganiser(Profile.getProfiles().getIdProfile());

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                EventDTO eventDTO = new EventDTO(result.getString(1), result.getString(2), LocalDate.parse(result.getString(3), formatter), LocalTime.parse(result.getString(4)), result.getString(5), Integer.parseInt(result.getString(6)), Integer.parseInt(result.getString(7)), Double.parseDouble(result.getString(8)));
                table.getItems().add(eventDTO);
            }
            result.getStatement().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showDistributors(TableView<DistributorDTO> table, ComboBox eventBox, String from, String to){
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

        table.getColumns().setAll(name,email,phone,rating,salary,event,seat,sold);

        PreparedStatement statement;

        boolean flag = !Objects.equals(from, "") && !Objects.equals(to, "");
        if(eventBox.getValue()!=null){
            if(flag){
                statement = QueriesRepository.selectDistributorsBetweenSoldTicketsAndEvent(Integer.parseInt(from),Integer.parseInt(to),Profile.getProfiles().getIdProfile(),eventBox.getValue().toString());
            } else if (!Objects.equals(from, "")) {
                statement = QueriesRepository.selectDistributorsMoreThenSoldTicketsAndEvent(Integer.parseInt(from),Profile.getProfiles().getIdProfile(),eventBox.getValue().toString());
            } else if (!Objects.equals(to, "")) {
                statement = QueriesRepository.selectDistributorsLessThenSoldTicketsAndEvent(Integer.parseInt(to),Profile.getProfiles().getIdProfile(),eventBox.getValue().toString());
            }
            else {
                statement = QueriesRepository.selectDistributorsByEvent(Profile.getProfiles().getIdProfile(),eventBox.getValue().toString());
            }
        }
        else {
            if (flag) {
                statement = QueriesRepository.selectDistributorsBetweenSoldTickets(Integer.parseInt(from),Integer.parseInt(to),Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(from, "")) {
                statement = QueriesRepository.selectDistributorsMoreThenSoldTickets(Integer.parseInt(from),Profile.getProfiles().getIdProfile());
            } else if (!Objects.equals(to, "")) {
                statement = QueriesRepository.selectDistributorsLessThenSoldTickets(Integer.parseInt(to),Profile.getProfiles().getIdProfile());
            } else {
                statement = QueriesRepository.selectDistributors(Profile.getProfiles().getIdProfile());
            }
        }

        try {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DistributorDTO distributorDTO =new DistributorDTO(result.getString(1)+" "+result.getString(2),result.getString(3),result.getString(4),Double.parseDouble(result.getString(5)),Double.parseDouble(result.getString(6)),result.getString(7),result.getString(8),Integer.parseInt(result.getString(9)));
                table.getItems().add(distributorDTO);
            }
            result.getStatement().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
