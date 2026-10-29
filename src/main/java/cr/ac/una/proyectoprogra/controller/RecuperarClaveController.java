/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.BufeteDto;
import cr.ac.una.proyectoprogra.service.UsuarioService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private MFXTextField txtCorreo;
    @FXML
    private MFXButton btnEnviar;
    
     private BufeteDto bufete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        txtCorreo.clear();
        bufete = (BufeteDto) AppContext.getInstance().get("BufeteRecuperar");
    }

    @FXML
    private void OnMouseClickedRegresar(MouseEvent event) {
        FlowController.getInstance().goViewInWindow("InicioSesionVista");
        ((Stage)lblRegresar.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnEnviar(ActionEvent event) {
        if (txtCorreo.getText() == null || txtCorreo.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperar contraseña", getStage(), "Digite el correo.");
            return;
        }
        UsuarioService usuarioService = new UsuarioService();
        Respuesta respuesta = usuarioService.recuperarClave(bufete.getBufId(), txtCorreo.getText());
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Recuperar contraseña", getStage(), respuesta.getMensaje());
            return;
        }
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Recuperar contraseña", getStage(), "Se envió una contraseña temporal a su correo. Ingrese con ella para definir una nueva.");
        FlowController.getInstance().goViewInWindow("InicioSesionView");
        ((Stage) btnEnviar.getScene().getWindow()).close();
    }
    
    
}
