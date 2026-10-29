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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class RegistrarUsuarioController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private ComboBox<?> cbxTipoId;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtApellido2;
    @FXML
    private ComboBox<?> cbxIdioma;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ImageView imgFoto;
    @FXML
    private Button btnFoto;
    @FXML
    private CheckBox chkAdmin;
    @FXML
    private TableView<?> tblPermisos;
    @FXML
    private TableColumn<?, ?> colVista;
    @FXML
    private TableColumn<?, ?> colConsultar;
    @FXML
    private TableColumn<?, ?> colCrear;
    @FXML
    private TableColumn<?, ?> colModificar;
    @FXML
    private TableColumn<?, ?> colEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnFoto(ActionEvent event) {
    }

    @FXML
    private void onActionChkAdmin(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }

    @Override
    public void initialize() {
        
    }
    
}
