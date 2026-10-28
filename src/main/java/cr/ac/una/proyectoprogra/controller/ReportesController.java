/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.ArchivoDto;
import cr.ac.una.proyectoprogra.service.ReporteService;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        Long abogadoId = 8L;   // temppp
        Respuesta respuesta = reporteService.getReportePrueba(abogadoId);

        if (!Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarError(respuesta.getMensaje());
            return;
        }

        ArchivoDto archivo = (ArchivoDto) respuesta.getResultado("Reporte");
        try {
            // Base64 -> bytes -> archivo temporal en la computadora del usuario
            byte[] pdf = Base64.getDecoder().decode(archivo.getContenido());
            Path temporal = Files.createTempFile("reporte_", ".pdf");
            Files.write(temporal, pdf);

            // Lo abre con el visor de PDF que tenga instalado el usuario
            Desktop.getDesktop().open(temporal.toFile());
        } catch (IOException ex) {
            mostrarError("No se pudo abrir el reporte: " + ex.getMessage());
        }
    }

    @FXML
    private void btnOnActionGenerarReporteContabilidad(ActionEvent event) {
    }

    @FXML
    private void btnOnActionGenerarReporteVEREMOS(ActionEvent event) {
    }

    @FXML
    private void btnOnActionBuscarAbogado(ActionEvent event) {

        FlowController.getInstance().goViewInWindowModal("BuscarAbogadoView", stage, Boolean.FALSE);

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

}
