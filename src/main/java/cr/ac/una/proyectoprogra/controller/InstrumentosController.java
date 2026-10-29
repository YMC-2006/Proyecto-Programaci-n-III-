package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.InstrumentoRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.service.EstadoInstrumentoService;
import cr.ac.una.proyectoprogra.service.InstrumentoService;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class InstrumentosController extends Controller implements Initializable {

    @FXML
    private Button btnGuardarInstrumento;
    @FXML
    private TabPane tabPane;
    @FXML
    private ComboBox<TipoInstrumento> cmbTipoInstrumento;
    @FXML
    private MFXTextField txtAbogadoTitular;
    @FXML
    private TextField txtHoraInstrumento;
    @FXML
    private TextField txtNumeroBoleta;
    @FXML
    private TextField txtEscritura;
    @FXML
    private TextField txtTomo;
    @FXML
    private TextField txtFolio;
    @FXML
    private TextField txtTomoAsiento;
    @FXML
    private CheckBox chkIndiceNotarial;
    @FXML
    private ComboBox<EstadoInstrumento> cmbEstado;
    @FXML
    private TextArea txaObservacionEstado;
    @FXML
    private TextArea txaDetalleInstrumento;
    @FXML
    private TextArea txaObservaciones;
    @FXML
    private MFXDatePicker dpFechaInstrumento;
    @FXML
    private MFXTextField txtMostrarFechaEstado;
    @FXML
    private CheckBox chkTributa;
    @FXML
    private TextField txtNumeroRecibo;
    @FXML
    private TextField txtMontoReal;
    @FXML
    private TextField txtMontoFactura;
    @FXML
    private Tab tabGeneralesDelInstrumento;
    @FXML
    private Tab tabPartesYpagos;
    @FXML
    private Tab tabRequisitos;
    @FXML
    private Tab tabEventos;
    @FXML
    private Tab tabDocumentoFinal;

    private Instrumento instrumentoActual;

    private TipoInstrumentoService tipoInstrumentoService = new TipoInstrumentoService();
    private EstadoInstrumentoService estadoInstrumentoService = new EstadoInstrumentoService();
    private InstrumentoService instrumentoService = new InstrumentoService();

    private Long abogadoTitularId;   // id del abogado elegido en el modal de busquda
    private List<TipoInstRequisito> requisitos;

    // para que acepte 9:30 y 09:30
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("H:mm");

    // requisitos del instrumento
    @FXML
    private TableView<InstrumentoRequisito> tblRequisitos;
    @FXML
    private TableColumn<InstrumentoRequisito, String> colRequisito;
    @FXML
    private TableColumn<InstrumentoRequisito, String> colEstado;
    @FXML
    private TableColumn<InstrumentoRequisito, Integer> colNumero;
    private ObservableList<InstrumentoRequisito> listaRequisitos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtNumeroBoleta.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtEscritura.setTextFormatter(Formato.getInstance().letrasFormat(20));

        // El número de recibo solo se habilita si el instrumento tributa
        txtNumeroRecibo.setDisable(true);
        chkTributa.selectedProperty().addListener((obs, antes, ahora) -> {
            txtNumeroRecibo.setDisable(!ahora);
            if (!ahora) {
                txtNumeroRecibo.clear();
            }
        });

        // que las demas pestañas queden desabilitadas mienstras se registra el instrumento
        tabPartesYpagos.setDisable(true);
        tabRequisitos.setDisable(false);
        tabEventos.setDisable(true);
        tabDocumentoFinal.setDisable(true);

        //----------------------------- Requisitos ------------------------------------
        colNumero.setCellValueFactory(new PropertyValueFactory<>("irqOrden"));
        colRequisito.setCellValueFactory(new PropertyValueFactory<>("irqDescripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("irqEstado"));
        
        cargarRequisitos();

    }

    @Override
    public void initialize() {
        configurarComboBoxes();
    }

    private void cargarRequisitos(){
        
        if(instrumentoActual == null){
            System.out.println("El instrumento esta vacido empty nulll");
            return;
        }
        
        List<InstrumentoRequisito> requisitos = instrumentoActual.getInstrumentoRequisitoList();
        if(requisitos != null){
            tblRequisitos.setItems(FXCollections.observableArrayList(requisitos));
            System.out.println("somehow se setearon los requisitos");
        }

        
    }
    
    private void configurarComboBoxes() {
        cmbTipoInstrumento.setConverter(new StringConverter<TipoInstrumento>() {
            @Override
            public String toString(TipoInstrumento tipo) {
                if (tipo == null) {
                    return "";
                }
                return tipo.getTinNombre();
            }

            @Override
            public TipoInstrumento fromString(String texto) {
                return null;   // el combo no es editable entonces no se usa
            }
        });

        // true = solo los tipos de instrumento activos
        Respuesta respuestaTipos = tipoInstrumentoService.getTipoInstrumentos(null, true, 1L);
        if (Boolean.TRUE.equals(respuestaTipos.getEstado())) {
            List<TipoInstrumento> tipos = (List<TipoInstrumento>) respuestaTipos.getResultado("TipoInstrumentos");
            cmbTipoInstrumento.setItems(FXCollections.observableArrayList(tipos));
        } else {
            mostrarError("Error obteniendo los tipos de instrumento: " + respuestaTipos.getMensaje());
        }

        cmbEstado.setConverter(new StringConverter<EstadoInstrumento>() {
            @Override
            public String toString(EstadoInstrumento estado) {
                if (estado == null) {
                    return "";
                }
                return estado.getEinDescripcion();
            }

            @Override
            public EstadoInstrumento fromString(String texto) {
                return null;
            }
        });

        // true = solo los estados activos
        Respuesta respuestaEstados = estadoInstrumentoService.getEstadoInstrumentos(true, null, null, 1L);
        if (Boolean.TRUE.equals(respuestaEstados.getEstado())) {
            List<EstadoInstrumento> estados = (List<EstadoInstrumento>) respuestaEstados.getResultado("EstadosInstrumentos");
            cmbEstado.setItems(FXCollections.observableArrayList(estados));
        } else {
            mostrarError("Error obteniendo los estados: " + respuestaEstados.getMensaje());
        }
    }

    @FXML
    private void onActionBtnBuscarAbogado(ActionEvent event) {
        AppContext.getInstance().delete("abogadoSeleccionado");
        FlowController.getInstance().goViewInWindowModal("BusquedaAbogadoView", stage, false);

        // Esto se ejecuta cuando se cierra el modal de búsqueda
        Abogado abogado = (Abogado) AppContext.getInstance().get("abogadoSeleccionado");
        if (abogado != null) {
            abogadoTitularId = abogado.getAboId();
            txtAbogadoTitular.setText(abogado.getAboNombre());
            AppContext.getInstance().delete("abogadoSeleccionado");
        }
    }

    @FXML
    private void onActionBtnGuardarInstrumento(ActionEvent event) {

        boolean instrumentoEsNuevo;
        if (instrumentoActual == null) {

            instrumentoEsNuevo = true;
        } else {
            instrumentoEsNuevo = false;
        }

        TipoInstrumento tipo = cmbTipoInstrumento.getSelectionModel().getSelectedItem();
        EstadoInstrumento estado = cmbEstado.getSelectionModel().getSelectedItem();
        LocalDate fecha = dpFechaInstrumento.getValue();
        LocalTime hora;
        BigDecimal montoReal = leerMonto(txtMontoReal);
        BigDecimal montoFacturado = leerMonto(txtMontoFactura);

        String observacionEstado = txaObservacionEstado.getText().trim();

        if (tipo == null) {
            mostrarError("Seleccione el tipo de instrumento");
            return;
        }

        if (abogadoTitularId == null) {
            mostrarError("Seleccione el abogado titular");
            return;
        }

        if (estado == null) {
            mostrarError("Seleccione el estado del instrumento");
            return;
        }

        if (fecha == null) {
            mostrarError("Seleccione la fecha del instrumento");
            return;
        }

        try {
            hora = LocalTime.parse(txtHoraInstrumento.getText().trim(), FORMATO_HORA);
        } catch (DateTimeParseException ex) {
            mostrarError("La hora debe tener el formato HH:mm, por ejemplo 09:30");
            txtHoraInstrumento.requestFocus();
            return;
        }

        if (montoReal == null) {
            mostrarError("El monto real no es un número válido");
            txtMontoReal.requestFocus();
            return;
        }

        if (montoFacturado == null) {
            mostrarError("El monto facturado no es un número válido");
            txtMontoFactura.requestFocus();
            return;
        }

        if (chkTributa.isSelected() && txtNumeroRecibo.getText().isBlank()) {
            mostrarError("Si el instrumento tributa, indique el número de recibo");
            txtNumeroRecibo.requestFocus();
            return;
        }

        // modo crear
        Instrumento instrumento;
        if (instrumentoEsNuevo) {
            instrumento = new Instrumento();
            instrumento.setInsBufeteId(1L);   // TODO: tomarlo de la sesión cuando funcione el login
            instrumento.setInsTipoId(tipo.getTinId());
            instrumento.setInsAbogadoId(abogadoTitularId);
            instrumento.setInsEstadoId(estado.getEinId());
        } else { // modo editar cuando ya cargue el inst
            instrumento = instrumentoActual;
        }

        instrumento.setInsObsEstado(txaObservacionEstado.getText());
        instrumento.setInsFechaHora(LocalDateTime.of(fecha, hora));
        instrumento.setInsBoleta(txtNumeroBoleta.getText().trim());
        instrumento.setInsEscritura(txtEscritura.getText().trim());
        instrumento.setInsTomo(txtTomo.getText().trim());
        instrumento.setInsFolio(txtFolio.getText().trim());
        instrumento.setInsTomoAsiento(txtTomoAsiento.getText().trim());
        instrumento.setInsIndiceNotarial(chkIndiceNotarial.isSelected());
        instrumento.setInsDetalle(txaDetalleInstrumento.getText());
        instrumento.setInsObservaciones(txaObservaciones.getText());

        instrumento.setInsTributa(chkTributa.isSelected());
        instrumento.setInsNumeroRecibo(txtNumeroRecibo.getText().trim());
        instrumento.setInsMontoReal(montoReal);
        instrumento.setInsMontoFacturado(montoFacturado);

        Respuesta respuesta = instrumentoService.guardarInstrumento(instrumento);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            if (instrumentoEsNuevo) {
                mostrarExito("Instrumento registrado exitosamenet");
                limpiarCampos();
            } else {
                mostrarExito("Instrumento modificado exitosamenet");
            }
        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    // Convertir el texto de un campo a bigDecimal
    private BigDecimal leerMonto(TextField campo) {
        String texto = campo.getText().trim().replace(",", ".");
        if (texto.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            BigDecimal monto = new BigDecimal(texto);
            if (monto.compareTo(BigDecimal.ZERO) < 0) {
                return null;
            }
            return monto;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @FXML
    private void onActionBtnLimpiarCampos(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        abogadoTitularId = null;

        cmbTipoInstrumento.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
        txtAbogadoTitular.clear();

        dpFechaInstrumento.setValue(null);
        txtHoraInstrumento.clear();
        txtMostrarFechaEstado.clear();
        txtNumeroBoleta.clear();
        txtEscritura.clear();
        txtTomo.clear();
        txtFolio.clear();
        txtTomoAsiento.clear();
        chkIndiceNotarial.setSelected(false);

        txaObservacionEstado.clear();
        txaDetalleInstrumento.clear();
        txaObservaciones.clear();

        chkTributa.setSelected(false);   // listener del chekbox limpia y deshabilita el recibo
        txtMontoReal.clear();
        txtMontoFactura.clear();
    }

    @FXML
    private void onActionBtnBuscarInstrumento(ActionEvent event) {
        // me falta editar un instrumento TOODOOOO
        AppContext.getInstance().delete("instrumentoSeleccionado");
        FlowController.getInstance().goViewInWindowModal("InstrumentosBusquedaView", stage, Boolean.FALSE);

        Instrumento instrumentoSeleccionado = (Instrumento) AppContext.getInstance().get("instrumentoSeleccionado");
        if (instrumentoSeleccionado == null) {
            return;
        }

        Respuesta respuesta = instrumentoService.getInstrumento(instrumentoSeleccionado.getInsId());
        if (respuesta.getEstado()) {
            instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
            cargarInstrumento(instrumentoActual);
            mostrarExito("Instrumento cargado");
        } else {
            mostrarError("Error cargando el instrumento");
        }
    }

    private void cargarInstrumento(Instrumento inst) {

        AppContext.getInstance().delete("instrumentoSeleccionado");
        txtEscritura.setText(inst.getInsEscritura());
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
