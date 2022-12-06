package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.EmailValidator;
import bg.tu_varna.sit.oop2_project.backend.PhoneValidator;
import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.collections.GetClients;
import bg.tu_varna.sit.oop2_project.backend.collections.GetEvents;
import bg.tu_varna.sit.oop2_project.backend.collections.GetSectors;
import bg.tu_varna.sit.oop2_project.backend.collections.GetTickets;
import bg.tu_varna.sit.oop2_project.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PurchaseController implements Initializable {
    private Stage stage;
    private Scene scene;
    private List<Tickets> ticketsList;
    private List<Sectors> sectorsList;
    private Tickets tickets;
    private Seats seats;
    private Sectors sectors;
    @FXML
    private ComboBox eventBox;
    @FXML
    private ComboBox seatsBox;
    @FXML
    private Label seat;
    @FXML
    private Button next2;
    @FXML
    private Label price;
    @FXML
    private Label name;
    @FXML
    private Label lastname;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label amount;
    @FXML
    private TextField name1;
    @FXML
    private TextField lastname1;
    @FXML
    private TextField phone1;
    @FXML
    private TextField email1;
    @FXML
    private TextField amount1;
    @FXML
    private Button buy;
    @FXML
    private Label error;
    @FXML
    private Label left;

    public void back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("distributor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }

    public void next(){
        if(eventBox.getValue()!=null) {
            seatsBox.getItems().clear();
            seat.setVisible(true);
            seatsBox.setVisible(true);
            next2.setVisible(true);

            sectorsList = GetSectors.get();
            List<String> list = new ArrayList<>();
            for (Event event : GetEvents.get()) {
                if (Objects.equals(event.getName(), eventBox.getValue().toString())) {
                    for (Sectors sectors : sectorsList) {
                        if (sectors.getEvent().getIdEvent() == event.getIdEvent())
                            list.add(sectors.getSeats().getType());
                    }
                }
            }
            seatsBox.getItems().addAll(list);
            price.setVisible(false);
            name.setVisible(false);
            name1.setVisible(false);
            lastname.setVisible(false);
            lastname1.setVisible(false);
            email.setVisible(false);
            email1.setVisible(false);
            phone.setVisible(false);
            phone1.setVisible(false);
            amount.setVisible(false);
            amount1.setVisible(false);
            buy.setVisible(false);
            left.setVisible(false);
            error.setVisible(false);
            Database.close();
        }
    }

    public void next2() {
        if(seatsBox.getValue()!=null) {
            for (Sectors sectors : sectorsList) {
                if (Objects.equals(sectors.getEvent().getName(), eventBox.getValue().toString()) && Objects.equals(sectors.getSeats().getType(), seatsBox.getValue().toString())) {
                    for (Tickets tickets : ticketsList) {
                        if (tickets.getDistributor().getIdProfile() == Profile.getProfiles().getIdProfile() && Objects.equals(sectors.getSeats().getIdSeats(), tickets.getSectors().getSeats().getIdSeats())) {
                            this.tickets = tickets;
                            this.sectors = sectors;
                            seats = sectors.getSeats();
                            price.setText("Цена на един билет (лв): " + tickets.getSectors().getSeats().getPrice());
                            break;
                        }
                    }
                }
            }
            price.setVisible(true);
            name.setVisible(true);
            name1.setVisible(true);
            lastname.setVisible(true);
            lastname1.setVisible(true);
            email.setVisible(true);
            email1.setVisible(true);
            phone.setVisible(true);
            phone1.setVisible(true);
            amount.setVisible(true);
            amount1.setVisible(true);
            buy.setVisible(true);
            name1.setText("");
            lastname1.setText("");
            email1.setText("");
            phone1.setText("");
            amount1.setText("");
            buy.setVisible(true);
            left.setVisible(false);
            error.setVisible(false);
        }
    }

    public void buy() throws SQLException {

        if(!Objects.equals(name1.getText(), "") && !Objects.equals(lastname1.getText(), "") && !Objects.equals(email1.getText(), "") && !Objects.equals(phone1.getText(), "") && !Objects.equals(amount1.getText(), "")) {
            if(PhoneValidator.validate(phone1.getText())) {
                if(EmailValidator.validate(email.getText())) {
                    Database.connection();
                    List<Client> clients = GetClients.get();
                    boolean flag = true;
                    for (Client client : clients) {
                        if (Objects.equals(client.getEmail(), email1.getText()) && sectors.getIdSectors() == client.getTicket().getSectors().getIdSectors()) {
                            if ((client.getQuantity() + Integer.parseInt(amount1.getText())) > seats.getTicketPerClient()) {
                                error.setVisible(true);
                                error.setText("Надвишавате лимита за купуване на билети");
                                left.setText("За момента имате " + client.getQuantity() + " броя купени билети. Може да купите още " + (seats.getTicketPerClient() - client.getQuantity()));
                                left.setVisible(true);
                            } else {
                                Connection connection = Database.connection();
                                Statement statement = connection.createStatement();
                                String sql = " UPDATE CLIENT SET QUANTITY=" + (client.getQuantity() + Integer.parseInt(amount1.getText())) + " WHERE EMAIL='" + client.getEmail() + "'";
                                statement.executeQuery(sql);
                                seats.setAmount(seats.getAmount() - client.getQuantity());
                                sql = "UPDATE SEATS SET amount = " + seats.getAmount() + " WHERE id_seats=" + seats.getIdSeats();
                                statement.executeQuery(sql);
                                seats.setSold(seats.getSold() + client.getQuantity());
                                sql = "UPDATE SEATS SET SOLD = " + seats.getSold() + " WHERE id_seats=" + seats.getIdSeats();
                                statement.executeQuery(sql);
                                tickets.setTicketsSold(tickets.getTicketsSold() + client.getQuantity());
                                sql = "UPDATE TICKETS SET TICKETSOLD = " + tickets.getTicketsSold() + " WHERE ID_TICKET=" + tickets.getIdTicket();
                                statement.executeQuery(sql);
                                left.setText("Покупката е успешна");
                                left.setVisible(true);
                                error.setVisible(false);

                                LogManager.shutdown();
                                System.setProperty("logFilename", "info.log");
                                Logger logger = LogManager.getLogger();
                                logger.info("Client added successful! client ID:" + client.getIdClient());

                                System.setProperty("logFilename", "organiser_id_" + tickets.getSectors().getEvent().getOrganiser().getIdProfile() + ".log");
                                logger = LogManager.getLogger();
                                logger.info(sectors.getEvent().getName() + "," + seats.getType() + "," + client.getQuantity());
                            }
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        if (Integer.parseInt(amount1.getText()) > seats.getTicketPerClient()) {
                            error.setVisible(true);
                            error.setText("Надвишавате лимита за купуване на билети");
                            left.setText("Може да купите максимум " + seats.getTicketPerClient() + " билета");
                            left.setVisible(true);
                        } else
                            insert();
                    }
                    Database.close();
                }else {
                    error.setVisible(true);
                    error.setText("Невалиден имейл!");
                }
            }else {
                error.setVisible(true);
                error.setText("Невалиден телефонен номер!");
            }
        }else {
            error.setVisible(true);
            error.setText("Попълнете всички полета!");
        }
    }

    public void insert() throws SQLException {
        Connection connection= Database.connection();
        Statement statement=connection.createStatement();

        String getId = "SELECT CLIENT_SEQUENCE.nextVal from DUAL";
        ResultSet rs = statement.executeQuery(getId);
        int id=0;
        if(rs.next())
            id = rs.getInt(1);

        Client client=new Client(id,name1.getText(),lastname1.getText(),email1.getText(),phone1.getText(),tickets,Integer.parseInt(amount1.getText()));
        String sql="INSERT INTO CLIENT(ID_CLIENT,FIRSTNAME, LASTNAME, EMAIL, PHONE, TICKET_ID, QUANTITY) VALUES ("+client.getIdClient()+",'"+client.getFirstName()+"','"+client.getLastName()+"','"+client.getEmail()+"','"+client.getPhoneNumber()+"',"+client.getTicket().getIdTicket()+","+client.getQuantity()+")";
        statement.executeQuery(sql);
        seats.setAmount(seats.getAmount()-client.getQuantity());
        sql="UPDATE SEATS SET amount = "+seats.getAmount()+" WHERE id_seats="+seats.getIdSeats();
        statement.executeQuery(sql);
        seats.setSold(seats.getSold()+client.getQuantity());
        sql="UPDATE SEATS SET SOLD = "+seats.getSold()+" WHERE id_seats="+seats.getIdSeats();
        statement.executeQuery(sql);
        tickets.setTicketsSold(tickets.getTicketsSold()+ client.getQuantity());
        sql="UPDATE TICKETS SET TICKETSOLD = "+tickets.getTicketsSold()+" WHERE ID_TICKET="+tickets.getIdTicket();
        statement.executeQuery(sql);
        left.setText("Покупката е успешна");
        left.setVisible(true);
        error.setVisible(false);

        LogManager.shutdown();
        System.setProperty("logFilename", "info.log");
        Logger logger = LogManager.getLogger();
        logger.info("Client added successful! client ID:"+client.getIdClient());

        LogManager.shutdown();
        System.setProperty("logFilename", "organiser_id_"+tickets.getSectors().getEvent().getOrganiser().getIdProfile()+".log");
        logger = LogManager.getLogger();
        logger.info(sectors.getEvent().getName()+","+seats.getType()+","+client.getQuantity());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Set<String> events=new HashSet<>();
        ticketsList = GetTickets.get();
        for(Tickets tickets: ticketsList) {
            if (tickets.getDistributor().getIdProfile() == Profile.getProfiles().getIdProfile())
                events.add(tickets.getSectors().getEvent().getName());
        }
        eventBox.getItems().addAll(events);
    }
}
