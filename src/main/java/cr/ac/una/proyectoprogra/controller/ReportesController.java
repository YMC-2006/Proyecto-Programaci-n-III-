/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Abogado, String> colPropietario;
    @FXML
    private TableColumn<Abogado, String> colNotario;
    
    private Abogado abogadoSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Lista para mostrar el abogado seleccionado
        tblAbogados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // para que las cols se ajusten al contenido

        colNombre.setCellValueFactory(new PropertyValueFactory<>("aboNombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("aboCedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("aboTelefono"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("aboCelular"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("aboCorreo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("aboDireccion"));
        colPropietario.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getAboPropietario()) ? "Sí" : "No"));
        colNotario.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getAboNotario()) ? "Sí" : "No"));

        this.tblAbogados.setPlaceholder(new Label("Busque un abogado para generar el reporte"));
    }

    @Override
    public void initialize() {
        this.tblAbogados.refresh();
    }

    @FXML
    private void btnOnActionGenerarReporteInstrumentos(ActionEvent event) {
        //Long abogadoId = 8L;   // temppp
        Respuesta respuesta = reporteService.getReportePrueba(abogadoSeleccionado.getAboId());

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

        FlowController.getInstance().goViewInWindowModal("BusquedaAbogadoView", stage, Boolean.FALSE);

    }

    public void cargarAbogado(Abogado abogado) {
        abogadoSeleccionado = abogado;
        this.tblAbogados.setItems(FXCollections.observableArrayList(abogado));
        System.out.println("despues de presionar guardar se cargo el empleado");
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
