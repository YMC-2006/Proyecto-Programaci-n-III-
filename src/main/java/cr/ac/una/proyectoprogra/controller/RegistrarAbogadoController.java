/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.service.AbogadoService;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class RegistrarAbogadoController extends Controller implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextArea txaDireccion;
    @FXML
    private CheckBox chkPropietario;
    @FXML
    private CheckBox chkNotario;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblTituloModal;
    private Abogado abogadoEditando;
    private AbogadoService abogadoService = new AbogadoService();
    @FXML
    private AnchorPane root;
    @FXML
    private Button btnCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // revisar
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(150));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(30));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtTelefono.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txtCelular.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txaDireccion.setTextFormatter(Formato.getInstance().maxLengthFormat(300));
    }

//    
    private void limpiarFormulario() {

    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {

        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String celular = txtCelular.getText();
        String direccion = txaDireccion.getText();
        boolean propietario = chkPropietario.isSelected();

        if (nombre.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.nombre"));
            txtNombre.requestFocus();
            return;
        }

        if (cedula.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.cedula"));
            txtCedula.requestFocus();
            return;
        }

        if (correo.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.correo"));
            txtCorreo.requestFocus();
            return;
        }

        if (telefono.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.telefono"));
            txtTelefono.requestFocus();
            return;
        }

        if (celular.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.celular"));
            txtCelular.requestFocus();
            return;
        }

        if (direccion.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.direccion"));
            txaDireccion.requestFocus();
            return;
        }

        // estoy creando
        if (abogadoEditando == null) {

            // mando notario false por el momento falta preguntarle al profe
            Abogado abogado = new Abogado(nombre, cedula, telefono, celular, correo, direccion, propietario, chkNotario.isSelected());
            abogado.setAboBufeteId(1L);
            Respuesta respuesta = abogadoService.guardarAbogado(abogado);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("abogado.exito.guardar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else { // estoy editando

            abogadoEditando.setAboNombre(nombre);
            abogadoEditando.setAboCedula(cedula);
            abogadoEditando.setAboCorreo(correo);
            abogadoEditando.setAboTelefono(telefono);
            abogadoEditando.setAboCelular(celular);
            abogadoEditando.setAboDireccion(direccion);
            abogadoEditando.setAboNotario(chkNotario.isSelected());
            abogadoEditando.setAboPropietario(chkPropietario.isSelected());
            abogadoEditando.setAboBufeteId(1L);
            Respuesta respuesta = abogadoService.guardarAbogado(abogadoEditando);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("abogado.exito.modificar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        }

    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCelular.clear();
        txaDireccion.clear();
        if (chkNotario.isSelected()) {
            chkNotario.setSelected(false);
        }
        if (chkPropietario.isSelected()) {
            chkPropietario.setSelected(false);
        }
        abogadoEditando = null;
        this.btnAgregar.setText("Guardar");
    }

    // se puede mejorar preguntar al profe
    public void cargarAbogado(Abogado abogado) {
        this.abogadoEditando = abogado;

        txtNombre.setText(abogado.getAboNombre());
        txtCedula.setText(abogado.getAboCedula());
        txtCorreo.setText(abogado.getAboCorreo());
        txtTelefono.setText(abogado.getAboTelefono());
        txtCelular.setText(abogado.getAboCelular());
        txaDireccion.setText(abogado.getAboDireccion());
        chkNotario.setSelected(abogado.getAboNotario());
        chkPropietario.setSelected(abogado.getAboPropietario());

        this.btnAgregar.setText("Editar");
    }

    public AnchorPane getRoot() {
        return root;
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();

    }

}
