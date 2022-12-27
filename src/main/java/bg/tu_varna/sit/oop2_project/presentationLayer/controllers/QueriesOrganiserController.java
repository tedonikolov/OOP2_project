package bg.tu_varna.sit.oop2_project.presentationLayer.controllers;

import bg.tu_varna.sit.oop2_project.busnessLayer.services.QueriesOrganiserService;
import bg.tu_varna.sit.oop2_project.busnessLayer.SceneChanger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.DistributorDTO;
import bg.tu_varna.sit.oop2_project.dataLayer.DTO.EventDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class QueriesOrganiserController implements Initializable {
    private Stage stage;
    private double stageWight;
    private double stageHigh;
    @FXML
    private Button back;
    @FXML
    private TableView<EventDTO> event;
    @FXML
    private TableView<DistributorDTO> distributor;
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker to;
    @FXML
    private TextField address;
    @FXML
    private TextField from2;
    @FXML
    private TextField to2;
    @FXML
    private ComboBox eventBox;
    @FXML
    private ComboBox eventBox1;



    public void back(ActionEvent event){
        SceneChanger.change(event,"organiser.fxml");
    }

    public void back2(){
        event.setVisible(false);
        distributor.setVisible(false);
        back.setVisible(false);
        stage.setWidth(stageWight);
        stage.setHeight(stageHigh);
    }

    public void clear(){
        address.clear();
        to.setValue(null);
        from.setValue(null);
        eventBox.setValue(null);
    }
    public void events(ActionEvent actionEvent) throws SQLException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageHigh = stage.getHeight();
        stageWight = stage.getWidth();
        stage.setWidth(event.getWidth());
        stage.setHeight(490);

        event.getItems().clear();
        distributor.setVisible(false);

        QueriesOrganiserService.showEvents(event,eventBox,from,to,address.getText());

        event.setVisible(true);
        back.setVisible(true);
    }



    public void distributors(ActionEvent actionEvent) throws SQLException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stageHigh = stage.getHeight();
        stageWight = stage.getWidth();
        stage.setWidth(distributor.getWidth());
        stage.setHeight(490);

        distributor.getItems().clear();
        event.setVisible(false);

        QueriesOrganiserService.showDistributors(distributor,eventBox1,from2.getText(),to2.getText());

        distributor.setVisible(true);
        back.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        QueriesOrganiserService.init(eventBox,eventBox1);
        eventBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(eventBox.getValue()!=null){
                    address.clear();
                    to.setDisable(true);
                    to.setValue(null);
                    from.setDisable(true);
                    from.setValue(null);
                    address.setDisable(true);
                }
                else {
                    from.setDisable(false);
                    to.setDisable(false);
                    address.setDisable(false);
                }
            }
        });
    }
}
