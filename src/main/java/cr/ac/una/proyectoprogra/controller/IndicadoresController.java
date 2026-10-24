/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class IndicadoresController extends Controller implements Initializable {

    @FXML
    private VBox cardAgenda;
    @FXML
    private HBox filaCentral;
    @FXML
    private Label txtIntrumentos;
    @FXML
    private Label txtIngresos;
    @FXML
    private Label txtGastos;
    @FXML
    private Label txtEventos;
    @FXML
    private BarChart<?, ?> grBarras;
    @FXML
    private ProgressBar prBarra;
    @FXML
    private TableView<?> lvAgenda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardAgenda.prefHeightProperty().bind(filaCentral.heightProperty());
    }    

    @Override
    public void initialize() {
    }
    
}
