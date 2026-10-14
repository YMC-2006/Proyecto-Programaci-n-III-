/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.service.ReporteService;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ReportesController extends Controller implements Initializable {

    @FXML
    private MFXDatePicker txtFiltroFechaInicio;
    @FXML
    private MFXDatePicker txtFiltroFechaFin;

    private final ReporteService reporteService = new ReporteService();
    @FXML
    private TableView<?> tblAbogados;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colCedula;
    @FXML
    private TableColumn<?, ?> colTelefono;
    @FXML
    private TableColumn<?, ?> colCelular;
    @FXML
    private TableColumn<?, ?> colCorreo;
    @FXML
    private TableColumn<?, ?> colDireccion;
    @FXML
    private TableColumn<?, ?> colPropietario;
    @FXML
    private TableColumn<?, ?> colNotario;

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
    private void btnOnActionGenerarReporteInstrumentos(ActionEvent event) {
        
    }

    @FXML
    private void btnOnActionGenerarReporteContabilidad(ActionEvent event) {
    }

    @FXML
    private void btnOnActionGenerarReporteVEREMOS(ActionEvent event) {
    }

    @FXML
    private void btnOnActionBuscarAbogado(ActionEvent event) {
    }

}
