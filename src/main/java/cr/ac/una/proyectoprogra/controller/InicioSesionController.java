/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.BufeteDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.BufeteService;
import cr.ac.una.proyectoprogra.service.UsuarioService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class InicioSesionController extends Controller implements Initializable {

    @FXML
    private MFXFilterComboBox<BufeteDto> cbxBufete;
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
    @FXML
    private Label lblReenviarActivacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblReenviarActivacion.setVisible(false);
        cbxBufete.selectedItemProperty().addListener((observable, anterior, nuevo) -> revisarActivacion());
    }    

    @Override
    public void initialize() {
        txtUsuario.clear();
        txtClave.clear();
        cbxBufete.clear();
    }

    @FXML
    private void OnActionBtnIngresar(ActionEvent event) {
        if (cbxBufete.getSelectedItem() == null) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), "Seleccione el bufete.");
            return;
        }
        if (txtUsuario.getText() == null || txtUsuario.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), "Digite el usuario.");
            return;
        }
        if (txtClave.getText() == null || txtClave.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), "Digite la clave.");
            return;
        }
        BufeteDto bufete = cbxBufete.getSelectedItem();
        UsuarioService usuarioService = new UsuarioService();
        Respuesta respuesta = usuarioService.getUsuario(bufete.getBufId(), txtUsuario.getText(), txtClave.getText());
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), respuesta.getMensaje());
            return;
        }
        UsuarioDto usuario = (UsuarioDto) respuesta.getResultado("Usuario");
        AppContext.getInstance().set("Usuario", usuario);
        AppContext.getInstance().set("Token", usuario.getToken());
        if (usuario.getUsrCambioClave()) {
            FlowController.getInstance().goViewInWindow("CambiarClaveView");
        } else {
            FlowController.getInstance().goMain();
        }
        ((Stage) btnIngresar.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnRegistrar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("RegistrarseView");
        ((Stage)btnRegistrar.getScene().getWindow()).close();
    }

    @FXML
    private void OnMouseClickedRecuperarClave(MouseEvent event) {
        if (cbxBufete.getSelectedItem() == null) {
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Recuperar contraseña", getStage(), "Digite su usuario, presione Enter y seleccione el bufete.");
        return;
        }
        AppContext.getInstance().set("BufeteRecuperar", cbxBufete.getSelectedItem());
        FlowController.getInstance().goViewInWindow("RecuperarClaveView");
        ((Stage) lblRecuperarClave.getScene().getWindow()).close();
    }

    @FXML
    private void btnOnActionEspanol(ActionEvent event) {
        FlowController.getInstance().cambiarIdioma("es");
    }

    @FXML
    private void btnOnActionIngles(ActionEvent event) {
        FlowController.getInstance().cambiarIdioma("en");
    }

    @FXML
    private void onKeyPressedTxtUsuario(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
        cargarBufetes();
        }
    }
    
    private void cargarBufetes() {
        cbxBufete.clearSelection();
        cbxBufete.getItems().clear();
        if (txtUsuario.getText() == null || txtUsuario.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), "Digite el usuario.");
            return;
        }
        BufeteService bufeteService = new BufeteService();
        Respuesta respuesta = bufeteService.getBufetesUsuario(txtUsuario.getText());
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), respuesta.getMensaje());
            return;
        }
        List<BufeteDto> bufetes = (List<BufeteDto>) respuesta.getResultado("Bufetes");
        if (bufetes.isEmpty()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), "No existe ningún bufete con ese usuario.");
            return;
        }
        cbxBufete.getItems().addAll(bufetes);
        if (bufetes.size() == 1) {
            cbxBufete.selectFirst();
            txtClave.requestFocus();
        } else {
            cbxBufete.requestFocus();
        }
    }

    @FXML
    private void OnMouseClickedReenviarActivacion(MouseEvent event) {
        if (cbxBufete.getSelectedItem() == null) {
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Reenviar activación", getStage(), "Digite su usuario, presione Enter y seleccione el bufete.");
        return;
        }
        UsuarioService usuarioService = new UsuarioService();
        Respuesta respuesta = usuarioService.reenviarActivacion(cbxBufete.getSelectedItem().getBufId(), txtUsuario.getText());
        if (!respuesta.getEstado()) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Reenviar activación", getStage(), respuesta.getMensaje());
            return;
        }
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Reenviar activación", getStage(), "Se envió un nuevo correo de activación. Revise su bandeja de entrada.");
    }
    
    private void revisarActivacion() {
        lblReenviarActivacion.setVisible(false);
        if (cbxBufete.getSelectedItem() == null) {
            return;
        }
        UsuarioService usuarioService = new UsuarioService();
        Respuesta respuesta = usuarioService.estaPendiente(cbxBufete.getSelectedItem().getBufId(), txtUsuario.getText());
        if (respuesta.getEstado()) {
            Boolean pendiente = (Boolean) respuesta.getResultado("Pendiente");
            lblReenviarActivacion.setVisible(pendiente);
        }
    }
}
