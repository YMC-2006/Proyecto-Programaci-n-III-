/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ContableController extends Controller implements Initializable {

    @FXML
    private ToggleGroup filtroTabla;
    @FXML
    private ToggleGroup tggTipo;
    @FXML
    private ToggleGroup tggTributa;
    @FXML
    private TableView<?> tblContable;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colDetalle;
    @FXML
    private TableColumn<?, ?> colOrigen;
    @FXML
    private TableColumn<?, ?> colTributa;
    @FXML
    private TableColumn<?, ?> colMonto;
    @FXML
    private ToggleButton tggIngreso;
    @FXML
    private ToggleButton tggGasto;
    @FXML
    private MFXDatePicker dtpFecha;
    @FXML
    private MFXTextField txtDetalle;
    @FXML
    private MFXTextField txtMonto;
    @FXML
    private MFXTextField txtFactura;
    @FXML
    private MFXRadioButton rdbTributa;
    @FXML
    private MFXRadioButton rdbNoTributa;
    @FXML
    private MFXButton btnAgregarSociedad;

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
    private void onActionBtnAgregarSociedad(ActionEvent event) {
    }
    
}
