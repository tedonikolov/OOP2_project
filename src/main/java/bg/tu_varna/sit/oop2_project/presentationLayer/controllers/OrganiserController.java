package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.Profile;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.dataLayer.collections.GetTickets;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Event;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Sectors;
import bg.tu_varna.sit.oop2_project.dataLayer.entities.Tickets;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrganiserController implements Initializable {
    @FXML
    private HBox notify;
    @FXML
    private GridPane grid;
    @FXML
    private Button close1;

    public void profile(ActionEvent event){
        SceneChanger.change(event,"profileOrganiser.fxml");
    }

    public void event(ActionEvent event){
        SceneChanger.change(event,"event.fxml");
    }

    public void seats(ActionEvent event){
        SceneChanger.change(event,"seats.fxml");
    }

    public void ticket(ActionEvent event){
        SceneChanger.change(event,"ticket.fxml");
    }

    public void rate(ActionEvent event){
        SceneChanger.change(event,"rate.fxml");
    }

    public void queries(ActionEvent event){
        SceneChanger.change(event,"queriesOrganiser.fxml");
    }

    public void login(ActionEvent event){
        SceneChanger.change(event,"login.fxml");
    }

    public void notification() throws IOException {
        File file=new File("organiser_id_" + Profile.getProfiles().getIdProfile() + ".log");
        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        Map<String,Map<String,Integer>> events= new HashMap<>();
        while ((strLine = br.readLine()) != null) {
            String[] string = strLine.split(" - ");
            String[] string1 = string[1].split(",");
            boolean flag=true;
            for(String event: events.keySet()){
                boolean flag2=true;
                if(Objects.equals(event, string1[0])){
                    Map<String,Integer> map=events.get(event);
                    for(String seat:map.keySet()){
                        if(Objects.equals(seat, string1[1])) {
                            map.put(seat,events.get(event).get(seat) + Integer.parseInt(string1[2]));
                            events.put(event, map);
                            flag2=false;
                            break;
                        }
                    }
                    if(flag2){
                        map.put(string1[1],Integer.parseInt(string1[2]));
                        events.put(string1[0],map);
                    }
                    flag=false;
                    break;
                }
            }
            if(flag){
                Map<String,Integer> map=new HashMap<>();
                map.put(string1[1],Integer.parseInt(string1[2]));
                events.put(string1[0],map);
            }
        }

        int i=1;
        for(String event: events.keySet()){
            Label label = new Label(event);
            grid.add(label, 0, i);
            Map<String,Integer> map=events.get(event);
            for(String seat:map.keySet()) {
                label = new Label(seat);
                grid.add(label, 1, i);
                label = new Label(map.get(seat).toString());
                grid.add(label, 2, i);
                i++;
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
