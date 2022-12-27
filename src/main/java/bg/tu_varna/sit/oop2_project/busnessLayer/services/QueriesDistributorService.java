package bg.tu_varna.sit.oop2_project.busnessLayer.services;

import bg.tu_varna.sit.oop2_project.busnessLayer.EventTable;
import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.EventDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.QueriesRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.SectorsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class QueriesDistributorService {
    public static void showEvents(TableView<EventDTO> table, ComboBox event, DatePicker from, DatePicker to, String address){
        PreparedStatement statement;
        EventTable.table(table);
        if (event.getValue() != null) {
            statement= QueriesRepository.selectEventAndDistributor(event.getValue().toString(),Profile.getProfiles().getIdProfile());
        } else if (from.getValue() != null && to.getValue() != null) {
            LocalDateTime fromDate = LocalDateTime.of(from.getValue().getYear(), from.getValue().getMonth(), from.getValue().getDayOfMonth(), 0, 0);
            LocalDateTime toDate = LocalDateTime.of(to.getValue().getYear(), to.getValue().getMonth(), to.getValue().getDayOfMonth(), 0, 0);
            if (Objects.equals(address, "")) {
                statement = QueriesRepository.selectByDateAndDistributor(fromDate,toDate,Profile.getProfiles().getIdProfile());
            } else {
                statement = QueriesRepository.selectByDateAndAddressAndDistributor(fromDate,toDate,address,Profile.getProfiles().getIdProfile());
            }
        } else if (!Objects.equals(address, "")) {
            statement = QueriesRepository.selectByAddressAndDistributor(address,Profile.getProfiles().getIdProfile());
        } else
            statement = QueriesRepository.selectAllAndDistributor(Profile.getProfiles().getIdProfile());

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

    public static void init(ComboBox eventBox){
        Set<String> events=new HashSet<>();
        events.add(null);
        for(Tickets tickets: TicketsRepository.get()){
            if(tickets.getDistributor().getIdProfile()== Profile.getProfiles().getIdProfile())
                events.add(tickets.getSectors().getEvent().getName());
        }
        eventBox.getItems().addAll(events);
    }
}
