package bg.tu_varna.sit.oop2_project.backend;

import bg.tu_varna.sit.oop2_project.backend.DTO.EventDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EventTable {
    public static void table(TableView<EventDTO> event) {
        TableColumn name = new TableColumn<EventDTO, String>("Събитие");
        name.setCellValueFactory(new PropertyValueFactory<EventDTO, String>("name"));

        TableColumn address = new TableColumn<EventDTO, String>("Адрес");
        address.setCellValueFactory(new PropertyValueFactory<EventDTO, String>("address"));

        TableColumn date = new TableColumn<EventDTO, String>("Дата");
        date.setCellValueFactory(new PropertyValueFactory<EventDTO, String>("date"));

        TableColumn time = new TableColumn<EventDTO, String>("Час");
        time.setCellValueFactory(new PropertyValueFactory<EventDTO, String>("time"));

        TableColumn seat = new TableColumn<EventDTO, String>("Място");
        seat.setCellValueFactory(new PropertyValueFactory<EventDTO, Integer>("seat"));

        TableColumn sold = new TableColumn<EventDTO, String>("Продадени билети");
        sold.setCellValueFactory(new PropertyValueFactory<EventDTO, Integer>("sold"));

        TableColumn amount = new TableColumn<EventDTO, String>("Оставащи билети");
        amount.setCellValueFactory(new PropertyValueFactory<EventDTO, Integer>("amount"));

        TableColumn price = new TableColumn<EventDTO, String>("Цена");
        price.setCellValueFactory(new PropertyValueFactory<EventDTO, Integer>("price"));

        event.getColumns().setAll(name, address, date, time, seat, sold, amount, price);
    }
}
