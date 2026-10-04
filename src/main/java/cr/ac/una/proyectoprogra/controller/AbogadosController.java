/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.util.FlowController;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class AbogadosController extends Controller implements Initializable {

    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TableView<Abogado> tblAbogados;
    @FXML
    private TableColumn<Abogado, String> colNombre;
    @FXML
    private TableColumn<Abogado, String> colCedula;
    @FXML
    private TableColumn<Abogado, String> colTelefono;
    @FXML
    private TableColumn<Abogado, String> colCelular;
    @FXML
    private TableColumn<Abogado, String> colCorreo;
    @FXML
    private TableColumn<Abogado, String> colDireccion;
    @FXML
    private TableColumn<Abogado, ?> colAcciones;
    @FXML
    private TableColumn<Abogado, Boolean> colPropietario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tblAbogados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // para que las cols se ajusten al contenido
       
    }    

    @Override
    public void initialize() {


    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("BusquedaAbogadoView");
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("RegistrarAbogadoView");
    }
    
}
