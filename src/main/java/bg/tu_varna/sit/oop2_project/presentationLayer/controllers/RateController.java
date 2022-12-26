package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;
import bg.tu_varna.sit.oop2_project.busnessLayer.services.RateService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

import static eu.hansolo.tilesfx.Tile.RED;

public class RateController implements Initializable{
    @FXML
    private ComboBox distributor;
    @FXML
    private ComboBox rate;
    @FXML
    private Label error;
    @FXML
    private Label label;

    public void rate(){
        if (rate.getValue() != null && distributor.getValue()!=null) {
            if(RateService.rate(this.distributor.getValue().toString(),Integer.parseInt(rate.getValue().toString()))){
                label.setText("Успешно оценихте " + this.distributor.getValue().toString());
                label.setVisible(true);
                error.setVisible(false);
            }else{
                error.setText("Вече сте оценили избрания разпространител");
                error.setVisible(true);
                error.setTextFill(RED);
                label.setVisible(false);
            }
        }else {
            error.setText("Изберете разпространител и оценка!");
            error.setVisible(true);
            error.setTextFill(RED);
        }
    }

    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
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

        RateService.init(distributor);
    }
}
