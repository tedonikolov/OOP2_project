package bg.tu_varna.sit.oop2_project.controllers;

import bg.tu_varna.sit.oop2_project.backend.Database;
import bg.tu_varna.sit.oop2_project.EventOrganizer;
import bg.tu_varna.sit.oop2_project.backend.Profile;
import bg.tu_varna.sit.oop2_project.backend.collections.GetTickets;
import bg.tu_varna.sit.oop2_project.entities.Event;
import bg.tu_varna.sit.oop2_project.entities.Sectors;
import bg.tu_varna.sit.oop2_project.entities.Tickets;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class OrganiserController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private HBox notify;
    @FXML
    private GridPane grid;
    @FXML
    private Button close1;

    public void profile(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("profileOrganiser.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void event(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("event.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void seats(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("seats.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void ticket(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("ticket.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void rate(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("rate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void queries(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EventOrganizer.class.getResource("queriesOrganiser.fxml"));
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

    public void notification() throws IOException {
        File file=new File("organiser_id_" + Profile.getProfiles().getIdProfile() + ".log");
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        int i=1;
        while ((strLine = br.readLine()) != null) {
            String[] string = strLine.split(" - ");
            String[] string1 = string[1].split(",");
            for(int j=0;j<string1.length;j++) {
                Label label = new Label(string1[j]);
                grid.add(label, j, i);
            }
            i++;
        }
        fstream.close();
        grid.setVisible(true);
        notify.setVisible(false);
        close1.setVisible(true);
        LogManager.shutdown();
        file.delete();
    }

    public void close(){
        grid.setVisible(false);
        close1.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File f =new File("organiser_id_"+Profile.getProfiles().getIdProfile()+".log");
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
                    if (tickets.getSectors().getEvent().getDateTime().isAfter(now) && tickets.getSectors().getEvent().getDateTime().isBefore(dateTime) && tickets.getSectors().getEvent().getOrganiser().getIdProfile() == Profile.getProfiles().getIdProfile()) {
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
        } catch (IOException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }

    }
}
