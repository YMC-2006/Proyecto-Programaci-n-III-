/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.CambioClaveDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.UsuarioService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class CambiarClaveController extends Controller implements Initializable  {

    @FXML
    private MFXButton  btnGuardar;
    @FXML
    private MFXPasswordField txtClaveActual;
    @FXML
    private MFXPasswordField txtClaveNueva;
    @FXML
    private MFXPasswordField txtClaveConfirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize() {
        txtClaveActual.clear();
        txtClaveNueva.clear();
        txtClaveConfirmar.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
 
    @FXML
    private void OnActionBtnGuardar(ActionEvent event) {
        if (txtClaveActual.getText() == null || txtClaveActual.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "Digite la contraseña temporal.");
            return;
        }
        if (txtClaveNueva.getText() == null || txtClaveNueva.getText().length() < 8 || txtClaveNueva.getText().length() > 30) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "La nueva contraseña debe tener entre 8 y 30 caracteres.");
            return;
        }
        if (!txtClaveNueva.getText().equals(txtClaveConfirmar.getText())) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "Las contraseñas no coinciden.");
            return;
        }
        if (txtClaveNueva.getText().equals(txtClaveActual.getText())) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "La nueva contraseña debe ser distinta de la temporal.");
            return;
        }
        UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("Usuario");
        CambioClaveDto cambioClave = new CambioClaveDto();
        cambioClave.setUsrId(usuario.getUsrId());
        cambioClave.setClaveActual(txtClaveActual.getText());
        cambioClave.setClaveNueva(txtClaveNueva.getText());
        UsuarioService usuarioService = new UsuarioService();
        Respuesta respuesta = usuarioService.cambiarClave(cambioClave);
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), respuesta.getMensaje());
            return;
        }
        usuario.setUsrCambioClave(false);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Cambiar contraseña", getStage(), "La contraseña se cambió correctamente.");
        FlowController.getInstance().goMain();
        ((Stage) btnGuardar.getScene().getWindow()).close();
    }
    
}
