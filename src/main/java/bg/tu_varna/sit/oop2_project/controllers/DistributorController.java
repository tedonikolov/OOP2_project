package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.collections.GetTickets;
import bg.tu_varna.sit.oop2_project.entities.Distributor;
import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Sectors;
import bg.tu_varna.sit.oop2_project.entities.Tickets;
import bg.tu_varna.sit.oop2_project.backend.collections.GetDistributors;
import bg.tu_varna.sit.oop2_project.backend.collections.GetEvents;
import bg.tu_varna.sit.oop2_project.backend.collections.GetSectors;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DistributorController implements Initializable{
    private Stage stage;
    private Scene scene;
    private List<Sectors> sectorsList;
    private Distributor distributor;
    private List<Event> events;
    @FXML
    private HBox notify;
    @FXML
    private GridPane grid;
    @FXML
    private Button close1;

    public void profile(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("profileDistributor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void purchase(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("purchase.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void queries(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("queriesDistributor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void login(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        if(Database.connection()!=null)
            Database.close();
    }

    public void notification() throws IOException, SQLException {
        File file=new File("distributor_id_" + Profile.getProfiles().getIdProfile() + ".log");
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        int i=1;
        while ((strLine = br.readLine()) != null) {
            String[] string = strLine.split(" - ");
            Label label = new Label(string[1]);
            grid.add(label, 0, i);
            i++;
        }
        fstream.close();
        grid.setVisible(true);
        notify.setVisible(false);
        close1.setVisible(true);
        LogManager.shutdown();
        file.delete();
    }

    public void close() {
        grid.setVisible(false);
        close1.setVisible(false);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events= GetEvents.get();
            sectorsList= GetSectors.get();
            for (Distributor distributor: GetDistributors.get()){
                if(distributor.getIdProfile()==Profile.getProfiles().getIdProfile())
                    this.distributor=distributor;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        File f =new File("distributor_id_"+ Profile.getProfiles().getIdProfile()+".log");
        if(f.exists()&&f.length()!=0) {
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(notify);
            transition.setByY(50);
            transition.setDuration(Duration.millis(1500));
            transition.play();
        }

        try {
            File f2=new File("date.log");
            if(f2.exists()&&f2.length()!=0 ){
                FileInputStream fstream = new FileInputStream(f2);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;

                LocalDateTime dateTime = null;
                LocalDateTime now = null;

                while ((strLine = br.readLine()) != null) {
                    RowConstraints row = new RowConstraints(30);
                    grid.getRowConstraints().add(row);
                    String[] string = strLine.split(" ");
                    LocalDate date = LocalDate.parse(string[0]);
                    now = date.atTime(0, 0);
                    date = LocalDate.parse(string[0]).plusDays(7);
                    dateTime = date.atTime(0, 0);
                }
                fstream.close();

                Set<Sectors> sectors = new HashSet<>();
                Set<Event> events = new HashSet<>();
                boolean flag = false;

                for (Tickets tickets : GetTickets.get()) {
                    if (tickets.getSectors().getEvent().getDateTime().isAfter(now) && tickets.getSectors().getEvent().getDateTime().isBefore(dateTime) && tickets.getDistributor().getIdProfile() == Profile.getProfiles().getIdProfile()) {
                        sectors.add(tickets.getSectors());
                        events.add(tickets.getSectors().getEvent());
                        flag = true;
                    }
                }

                if (flag) {
                    for (Event event : events) {
                        int left = 0;
                        for (Sectors sector : sectors) {
                            if (event == sector.getEvent())
                                left += sector.getSeats().getAmount();
                        }
                        if (left > 0) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Подсещане");
                            alert.setHeaderText(event.getName() + " дата: " + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(event.getDateTime()));
                            alert.setContentText("Остават още " + left + " билета");
                            alert.showAndWait();
                        }
                    }
                }
                LogManager.shutdown();
                f2.delete();
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
