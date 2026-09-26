/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
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
 * @author vjjcu
 */
public class InicioSesionController extends Controller implements Initializable {

    @FXML
    private MFXFilterComboBox<?> cbxBufete;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private MFXButton btnIngresar;
    @FXML
    private MFXButton btnRegistrar;
    @FXML
    private Label lblRecuperarClave;

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
    private void OnActionBtnIngresar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("PrincipalView");
        ((Stage)btnIngresar.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnRegistrar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("RegistrarseView");
        ((Stage)btnRegistrar.getScene().getWindow()).close();
    }

    @FXML
    private void OnMouseClickedRecuperarClave(MouseEvent event) {
        FlowController.getInstance().goViewInWindow("RecuperarClaveView");
        ((Stage)lblRecuperarClave.getScene().getWindow()).close();
    }
    
}
