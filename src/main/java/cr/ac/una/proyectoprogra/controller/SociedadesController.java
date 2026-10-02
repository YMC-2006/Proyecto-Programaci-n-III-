/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class SociedadesController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("BusquedaSociedadView", stage, Boolean.FALSE);
    }

    @FXML
    private void onActionBtnNueva(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("RegistrarSociedadView", stage, Boolean.FALSE);
    }

    @FXML
    private void onActionBtnEditar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAgregarRepresentante(ActionEvent event) {
    }
    
}
