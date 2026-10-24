/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class AgendaController extends Controller implements Initializable {

    @FXML
    private GridPane gridCalendario; 
    private YearMonth mesActual = YearMonth.now(); // navegar entre meses es solo cambiar el mes actual
    private int columnaSemanaInicio = 0;
    private LocalDate diaSemanaDiaUno = LocalDate.now();
    /*
    mi columna de inicio sera igual a al dia de la semana de inicio menos uno
    por ejemplo una mes donde el dia 1 cae un lunes, si le resto 1, queda en 0 esa sera mi primera coluna
    */
    @FXML
    private Button btnAnterior;
    @FXML
    private Label lblMesAnio;
    @FXML
    private Button btnSiguiente;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gridCalendario.getChildren().clear();
    }    

    @Override
    public void initialize() {

    }
    
    
    
    private void generarCalendario(){

    }

    @FXML
    private void onMesAnterior(ActionEvent event) {
    }

    @FXML
    private void onMesSiguiente(ActionEvent event) {
    }
    
}
