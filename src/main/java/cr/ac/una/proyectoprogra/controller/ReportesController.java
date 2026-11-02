/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.model.ArchivoDto;
import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.model.ResultadoInstrumento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.ReporteService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ReportesController extends Controller implements Initializable {

    private final ReporteService reporteService = new ReporteService();

    @FXML
    private MFXTextField txtParteElegida;
    @FXML
    private RadioButton rdbCliente;
    @FXML
    private ToggleGroup grupoFiltro;
    @FXML
    private RadioButton rdbAbogado;
    @FXML
    private TableView<ResultadoInstrumento> tblFiltros;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colEscritura;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colTipo;
    @FXML
    private TableColumn<ResultadoInstrumento, LocalDateTime> colFecha;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colEstado;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colParte;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colAcciones;

    private Long abogadoSeleccionadoId;   // id del abogado elegido en el modal de busquda
    private Long clienteSeleccionadoId;
    @FXML
    private MFXDatePicker dpFechaInicioFiltro;
    @FXML
    private MFXDatePicker dpFechaFinFiltro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colEscritura.setCellValueFactory(new PropertyValueFactory<>("rinEscritura"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("rinTipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("rinEstado"));
        colParte.setCellValueFactory(new PropertyValueFactory<>("rinParte"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("rinFecha"));
        colFecha.setCellFactory(columna -> new TableCell<ResultadoInstrumento, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime fecha, boolean empty) {
                super.updateItem(fecha, empty);
                if (empty || fecha == null) {
                    setText(null);
                    return;
                }
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                setText(fecha.format(formato));
            }
        });

        grupoFiltro.selectedToggleProperty().addListener((obs, antes, ahora) -> {
            clienteSeleccionadoId = null;
            abogadoSeleccionadoId = null;
            txtParteElegida.clear();
        });
        rdbAbogado.setSelected(true);

    }

    @Override
    public void initialize() {
        rdbAbogado.setSelected(true); 
        abogadoSeleccionadoId = null;
        clienteSeleccionadoId = null;
        txtParteElegida.clear();
        dpFechaInicioFiltro.setValue(null);
        dpFechaFinFiltro.setValue(null);
    }

    @FXML
    private void btnOnActionGenerarReporteInstrumentos(ActionEvent event) {

        if (abogadoSeleccionadoId == null && clienteSeleccionadoId == null) {
            if (rdbAbogado.isSelected()) {
                mostrarError("Seleccione un abogado");
            } else {
                mostrarError("Seleccione un cliente");
            }
            return;
        }

        LocalDate desde = dpFechaInicioFiltro.getValue();
        LocalDate hasta = dpFechaFinFiltro.getValue();
        if (desde == null || hasta == null) {
            mostrarError("Seleccione la fecha de inicio y la fecha final. Para un solo día, use la misma fecha en las dos.");
            return;
        }
        if (desde.isAfter(hasta)) {
            mostrarError("La fecha de inicio no puede ser mayor que la fecha final");
            return;
        }

        Respuesta respuesta = reporteService.getReporteInstrumentos(bufeteCargado(), abogadoSeleccionadoId, clienteSeleccionadoId, desde, hasta);
        if (!Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarError(respuesta.getMensaje());
            return;
        }

        abrirPdf((ArchivoDto) respuesta.getResultado("Reporte"));
    }

    @FXML
    private void btnOnActionGenerarReporteContabilidad(ActionEvent event) {
    }

    @FXML
    private void btnOnActionGenerarReporteVEREMOS(ActionEvent event) {
    }

    private void cargarInstrumentosDeAbogado(Abogado abogado) {

    }

    private void cargarInstrumentosDeCliente(Cliente cliente) {

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
    private void btnOnActionBuscar(ActionEvent event) {

        if (rdbAbogado.isSelected()) {
            AppContext.getInstance().delete("abogadoSeleccionado");

            FlowController.getInstance().goViewInWindowModal("BusquedaAbogadoView", stage, Boolean.FALSE);
            Abogado abogado = (Abogado) AppContext.getInstance().get("abogadoSeleccionado");

            if (abogado != null) {
                abogadoSeleccionadoId = abogado.getAboId();
                txtParteElegida.setText(abogado.getAboNombre());
                AppContext.getInstance().delete("abogadoSeleccionado");

            }

        } else {
            AppContext.getInstance().delete("clienteSeleccionado");
            FlowController.getInstance().goViewInWindowModal("BuscarClienteView", stage, Boolean.FALSE);
            Cliente cliente = (Cliente) AppContext.getInstance().get("clienteSeleccionado");

            if (cliente != null) {
                clienteSeleccionadoId = cliente.getCliId();
                txtParteElegida.setText(cliente.getCliNombre() + " " + cliente.getCliApellido1() + " " + cliente.getCliApellido2());
                AppContext.getInstance().delete("clienteSeleccionado");

            }

        }

    }

    private Long bufeteCargado() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        return usuarioSesion.getUsrBufeteId();
    }

    private void abrirPdf(ArchivoDto archivo) {
        try {
            byte[] pdf = Base64.getDecoder().decode(archivo.getContenido());
            Path temporal = Files.createTempFile("reporte_", ".pdf");
            Files.write(temporal, pdf);
            Desktop.getDesktop().open(temporal.toFile());
        } catch (IOException ex) {
            mostrarError("No se pudo abrir el reporte: " + ex.getMessage());
        }
    }

}
