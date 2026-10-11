/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class RegistrarEstadoInstrumentoController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private CheckBox chkNotifica;
    @FXML
    private CheckBox chkEstadoFinal;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
    }

    @Override
    public void initialize() {

    }
    
}
