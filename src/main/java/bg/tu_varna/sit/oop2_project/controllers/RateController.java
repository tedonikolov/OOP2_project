package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.SceneChanger;
import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Tickets;
import bg.tu_varna.sit.oop2_project.backend.collections.GetDistributors;
import bg.tu_varna.sit.oop2_project.backend.collections.GetEvents;
import bg.tu_varna.sit.oop2_project.backend.collections.GetTickets;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import static eu.hansolo.tilesfx.Tile.RED;
import static javafx.scene.paint.Color.BLACK;

public class RateController implements Initializable{
    private List<Tickets> tickets;
    private List<Distributor> distributors;
    @FXML
    private ComboBox distributor;
    @FXML
    private ComboBox rate;
    @FXML
    private Label error;
    @FXML
    private Label label;

    public void rate() throws SQLException, ParseException {
        if (rate.getValue() != null && distributor.getValue()!=null) {
            Connection connection = Database.connection();
            Statement statement = connection.createStatement();
            Map<Integer, Double> distributors = new HashMap<>();

            for (Event event : GetEvents.get()) {
                if (event.getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile()) {
                    String sql = "SELECT DISTRIBUTOR_ID, RATE FROM TICKETS JOIN SECTORS S on S.ID_SECTORS = TICKETS.SECTORS_ID WHERE EVENT_ID=" + event.getIdEvent();
                    ResultSet rs = statement.executeQuery(sql);
                    while (rs.next()) {
                        distributors.put(Integer.parseInt(rs.getString(1)), Double.parseDouble(rs.getString(2)));
                    }
                }
            }

            String string = this.distributor.getValue().toString();
            String[] name = string.split(" ");

            for (Distributor distributor : this.distributors) {
                if (Objects.equals(distributor.getFirstName(), name[0]) && Objects.equals(distributor.getLastName(), name[1])) {
                    for (Tickets tickets : tickets) {
                        if (tickets.getDistributor().getIdProfile() == distributor.getIdProfile()) {
                            if (tickets.getRate() == 0) {
                                double rating = distributor.getRating();
                                int curr = Integer.parseInt(rate.getValue().toString());

                                if (rating == 0)
                                    rating = curr;
                                else {
                                    rating += curr;
                                    rating = rating / 2;
                                    DecimalFormat df = new DecimalFormat("0.00");
                                    rating = (Double) df.parse(df.format(rating));
                                }

                                String sql = " UPDATE TICKETS SET RATE=" + curr + " WHERE DISTRIBUTOR_ID=" + distributor.getIdProfile();
                                statement.executeQuery(sql);
                                sql = " UPDATE DISTRIBUTOR SET RATING=" + rating + " WHERE ID_PROFILE=" + distributor.getIdProfile();
                                statement.executeQuery(sql);
                                label.setText("Успешно оценихте " + tickets.getDistributor().getFirstName());
                                label.setVisible(true);

                                LogManager.shutdown();
                                System.setProperty("logFilename", "info.log");
                                Logger logger = LogManager.getLogger();
                                logger.info("Distributor rated successful! profile ID:" + distributor.getIdProfile());
                            } else {
                                error.setText("Вече сте оценили избрания разпространител");
                                error.setVisible(true);
                                error.setTextFill(BLACK);
                                label.setText("Оценка: " + tickets.getRate());
                                label.setVisible(true);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            Database.close();
        }else {
            error.setText("Изберете разпространител и оценка!");
            error.setVisible(true);
            error.setTextFill(RED);
        }
    }

    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
        if(Database.connection()!=null)
            Database.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> rates=new ArrayList<>();
        rates.add(1);
        rates.add(2);
        rates.add(3);
        rates.add(4);
        rates.add(5);
        rates.add(6);
        rates.add(7);
        rates.add(8);
        rates.add(9);
        rates.add(10);
        rate.getItems().addAll(rates);

        Set<String> distributors=new HashSet<>();
        tickets=GetTickets.get();
        this.distributors=GetDistributors.get();
        for(Tickets ticket : tickets) {
            for (Distributor distributor : this.distributors) {
                if (ticket.getSectors().getEvent().getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile() && ticket.getDistributor().getIdProfile() == distributor.getIdProfile()) {
                    String fullName = distributor.getFirstName() + " " + distributor.getLastName();
                    distributors.add(fullName);
                }
            }
        }
        distributor.getItems().addAll(distributors);
    }
}
