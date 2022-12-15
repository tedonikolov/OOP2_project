package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.repositories.TicketsRepository;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DistributorController implements Initializable{
    @FXML
    private HBox notify;
    @FXML
    private GridPane grid;
    @FXML
    private Button close1;

    public void profile(ActionEvent event){
        SceneChanger.change(event,"profileDistributor.fxml");
    }

    public void purchase(ActionEvent event){
        SceneChanger.change(event,"purchase.fxml");
    }

    public void queries(ActionEvent event){
        SceneChanger.change(event,"queriesDistributor.fxml");
    }

    public void login(ActionEvent event){
        SceneChanger.change(event,"login.fxml");
    }

    public void notification() throws IOException{
        File file=new File("distributor_id_" + Profile.getProfiles().getIdProfile() + ".log");
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        Set<String> events=new HashSet<>();

        while ((strLine = br.readLine()) != null) {
            String[] string = strLine.split(" - ");
            events.add(string[1]);
        }

        int i=1;
        for(String event:events){
            Label label = new Label(event);
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

                for (Tickets tickets : TicketsRepository.get()) {
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
        } catch (IOException e) {
            LogManager.shutdown();
            System.setProperty("logFilename", "fatal.log");
            Logger logger = LogManager.getLogger();
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }
}
