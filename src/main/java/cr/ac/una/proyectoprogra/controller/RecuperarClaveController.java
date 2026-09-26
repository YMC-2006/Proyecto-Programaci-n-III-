/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class RecuperarClaveController extends Controller implements Initializable {

    @FXML
    private Label lblRegresar;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXButton btnEnviar;

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
    private void OnMouseClickedRegresar(MouseEvent event) {
        FlowController.getInstance().goViewInWindow("InicioSesionVista");
        ((Stage)lblRegresar.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnEnviar(ActionEvent event) {
    }
    
}
