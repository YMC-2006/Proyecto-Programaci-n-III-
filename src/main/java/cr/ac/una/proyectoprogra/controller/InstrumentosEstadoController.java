/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class InstrumentosEstadoController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TextField txtBuscarDescripcion;
    @FXML
    private ComboBox<?> cmbFiltroEstado;
    @FXML
    private ComboBox<?> cmbFiltroNotifica;
    @FXML
    private TableView<?> tblEstadosInstrumento;
    @FXML
    private TableColumn<?, ?> colDescripcion;
    @FXML
    private TableColumn<?, ?> colEstado;
    @FXML
    private TableColumn<?, ?> colNotifica;
    @FXML
    private TableColumn<?, ?> colFinal;
    @FXML
    private TableColumn<?, ?> colAcciones;

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
    private void onActionBtnAgregar(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("RegistrarEstadoInstrumentoView", stage, Boolean.FALSE);
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
    }
    
}
