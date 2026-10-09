/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.service.ClienteService;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class RegistrarClienteController extends Controller implements Initializable {
 
    @FXML
    private Label lblTituloModal;
    @FXML
    private ComboBox<String> cbxTipoId;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtEstadoCivil;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtOcupacion;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtObservaciones;
    @FXML
    private TextField txtDireccion;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private AnchorPane root;
 
    private Cliente clienteEditando;
    private ClienteService clienteService = new ClienteService();
 
    private final String[] siglasTipoId = {"FIS", "JUR", "DIM", "PAS", "NIT"};
    private final String[] nombresTipoId = {"Física nacional", "Jurídica", "DIMEX", "Pasaporte", "NITE"};
 
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbxTipoId.getItems().setAll(nombresTipoId);
        cbxTipoId.getSelectionModel().selectFirst();
 
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(30));
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(60));
        txtApellido1.setTextFormatter(Formato.getInstance().letrasFormat(60));
        txtApellido2.setTextFormatter(Formato.getInstance().letrasFormat(60));
        txtEstadoCivil.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtOcupacion.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtCelular.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtDireccion.setTextFormatter(Formato.getInstance().maxLengthFormat(300));
        txtObservaciones.setTextFormatter(Formato.getInstance().maxLengthFormat(1000));
    }
 
    @Override
    public void initialize() {
 
    }
 
    private void mostrarError(String mensaje) {
        
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
 
    private void mostrarExito(String mensaje) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
 
    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
 
        int indiceTipo = cbxTipoId.getSelectionModel().getSelectedIndex();
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String apellido1 = txtApellido1.getText();
        String apellido2 = txtApellido2.getText();
        String estadoCivil = txtEstadoCivil.getText();
        String ocupacion = txtOcupacion.getText();
        String celular = txtCelular.getText();
        String correo = txtCorreo.getText();
        String direccion = txtDireccion.getText();
        String observaciones = txtObservaciones.getText();
 
        if (indiceTipo < 0) {
            mostrarError(FlowController.getIdioma().getString("cliente.error.tipoId"));
            cbxTipoId.requestFocus();
            return;
        }
 
        if (cedula.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("cliente.error.cedula"));
            txtCedula.requestFocus();
            return;
        }
 
        if (nombre.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("cliente.error.nombre"));
            txtNombre.requestFocus();
            return;
        }
 
        if (apellido1.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("cliente.error.apellido1"));
            txtApellido1.requestFocus();
            return;
        }
 
        if (clienteEditando == null) {
 
            Cliente cliente = new Cliente();
            cliente.setCliTipoIdentificacion(siglasTipoId[indiceTipo]);
            cliente.setCliIdentificacion(cedula);
            cliente.setCliNombre(nombre);
            cliente.setCliApellido1(apellido1);
            cliente.setCliApellido2(apellido2);
            cliente.setCliEstadoCivil(estadoCivil);
            cliente.setCliOcupacion(ocupacion);
            cliente.setCliCelular(celular);
            cliente.setCliCorreo(correo);
            cliente.setCliDireccion(direccion);
            cliente.setCliObservaciones(observaciones);
            cliente.setCliBufeteId(1L); // Por el momento va a ser el bufete 1 porque no he hcho lo del login
            Respuesta respuesta = clienteService.guardarCliente(cliente);
 
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                
                mostrarExito(FlowController.getIdioma().getString("cliente.exito.guardar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }
 
        } else {
 
            clienteEditando.setCliTipoIdentificacion(siglasTipoId[indiceTipo]);
            clienteEditando.setCliIdentificacion(cedula);
            clienteEditando.setCliNombre(nombre);
            clienteEditando.setCliApellido1(apellido1);
            clienteEditando.setCliApellido2(apellido2);
            clienteEditando.setCliEstadoCivil(estadoCivil);
            clienteEditando.setCliOcupacion(ocupacion);
            clienteEditando.setCliCelular(celular);
            clienteEditando.setCliCorreo(correo);
            clienteEditando.setCliDireccion(direccion);
            clienteEditando.setCliObservaciones(observaciones);
            clienteEditando.setCliBufeteId(1L);
            Respuesta respuesta = clienteService.guardarCliente(clienteEditando);
            
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("cliente.exito.modificar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }
 
        }
 
    }
 
    private void limpiarCampos() {
        cbxTipoId.getSelectionModel().selectFirst();
        txtCedula.clear();
        txtNombre.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtEstadoCivil.clear();
        txtOcupacion.clear();
        txtCelular.clear();
        txtCorreo.clear();
        txtDireccion.clear();
        txtObservaciones.clear();
        clienteEditando = null;
        this.btnAgregar.setText("Guardar");
    }
 
    public void cargarCliente(Cliente cliente) {
        this.clienteEditando = cliente;
 
        int indiceTipo = Arrays.asList(siglasTipoId).indexOf(cliente.getCliTipoIdentificacion());
        if (indiceTipo >= 0) {
            cbxTipoId.getSelectionModel().select(indiceTipo);
        }
        txtCedula.setText(cliente.getCliIdentificacion());
        txtNombre.setText(cliente.getCliNombre());
        txtApellido1.setText(cliente.getCliApellido1());
        txtApellido2.setText(cliente.getCliApellido2());
        txtEstadoCivil.setText(cliente.getCliEstadoCivil());
        txtOcupacion.setText(cliente.getCliOcupacion());
        txtCelular.setText(cliente.getCliCelular());
        txtCorreo.setText(cliente.getCliCorreo());
        txtDireccion.setText(cliente.getCliDireccion());
        txtObservaciones.setText(cliente.getCliObservaciones());
 
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
 