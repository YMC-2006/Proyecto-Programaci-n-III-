/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class UsuariosController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnAgregarUsuario;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarApellido;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private TextField txtBuscarIdentificacion;
    @FXML
    private TextField txtBuscarCorreo;
    @FXML
    private TableView<?> tblUsuarios;
    @FXML
    private TableColumn<?, ?> colUsuario;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colApellido1;
    @FXML
    private TableColumn<?, ?> colApellido2;
    @FXML
    private TableColumn<?, ?> colIdentificacion;
    @FXML
    private TableColumn<?, ?> colCorreo;
    @FXML
    private TableColumn<?, ?> colAdmin;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TableColumn<?, ?> colAcciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBtnAgregarUsuario(ActionEvent event) {
        AppContext.getInstance().delete("usuarioEditar");
        FlowController.getInstance().goViewInWindowModal("RegistrarUsuarioView", stage, false);
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
    }

    @Override
    public void initialize() {
    }
    
}
