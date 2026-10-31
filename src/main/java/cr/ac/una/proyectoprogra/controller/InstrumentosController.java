package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
import cr.ac.una.proyectoprogra.model.Evento;
import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.InstrumentoGasto;
import cr.ac.una.proyectoprogra.model.InstrumentoRequisito;
import cr.ac.una.proyectoprogra.model.ResultadoInstrumento;
import cr.ac.una.proyectoprogra.model.TipoInstRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.EstadoInstrumentoService;
import cr.ac.una.proyectoprogra.service.EventoService;
import cr.ac.una.proyectoprogra.service.InstrumentoGastoService;
import cr.ac.una.proyectoprogra.service.InstrumentoService;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Permisos;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private InstrumentoGastoService instrumentoGastoService = new InstrumentoGastoService();
    private EventoService eventoService = new EventoService();

    private Long abogadoTitularId;   // id del abogado elegido en el modal de busquda

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
    @FXML
    private Tab tabContable;

    @FXML
    private VBox vbGastos;
    @FXML
    private TextField txtGastoDetalle;
    @FXML
    private MFXDatePicker dpGastoFecha;
    @FXML
    private TextField txtGastoFactura;
    @FXML
    private TextField txtGastoMonto;
    @FXML
    private CheckBox chkGastoTributa;
    @FXML
    private Button btnAgregarGasto;
    @FXML
    private TableView<InstrumentoGasto> tblGastos;
    @FXML
    private TableColumn<InstrumentoGasto, String> colGastoDetalle;
    @FXML
    private TableColumn<InstrumentoGasto, LocalDate> colGastoFecha;
    @FXML
    private TableColumn<InstrumentoGasto, String> colGastoFactura;
    @FXML
    private TableColumn<InstrumentoGasto, BigDecimal> colGastoMonto;
    @FXML
    private TableColumn<InstrumentoGasto, String> colGastoTributa;
    @FXML
    private TableColumn<InstrumentoGasto, InstrumentoGasto> colGastoAcciones;
    @FXML
    private Label lblTotalGastos;
    private ObservableList<InstrumentoGasto> listaGastos = FXCollections.observableArrayList();

    @FXML
    private TableView<Evento> tblEventos;
    @FXML
    private TableColumn<Evento, String> colEventoDetalle;
    @FXML
    private TableColumn<Evento, LocalDateTime> colEventoFechaHora; // checkkkk
    @FXML
    private TableColumn<Evento, String> colEventoAtendido;
    @FXML
    private TextField txtEventoDetalle;
    @FXML
    private MFXDatePicker dpEventoFecha;
    @FXML
    private TextField txtEventoHora;
    @FXML
    private Button btnAgregarEvento;
    @FXML
    private TableColumn<Evento, Evento> colEventoAcciones;
    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();

    private InstrumentoGasto gastoEditando;   // null = agregando uno nuevo
    private Evento eventoEditando;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNumeroBoleta.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtEscritura.setTextFormatter(Formato.getInstance().letrasFormat(20));
        
        txtHoraInstrumento.setTextFormatter(Formato.getInstance().maxLengthFormat(5));
        txtMostrarFechaEstado.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtNumeroBoleta.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtEscritura.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtTomoAsiento.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtFolio.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtTomoAsiento.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtNumeroRecibo.setTextFormatter(Formato.getInstance().maxLengthFormat(30));

        txaObservacionEstado.setTextFormatter(Formato.getInstance().letrasFormat(500));
        txaDetalleInstrumento.setTextFormatter(Formato.getInstance().letrasFormat(200));
        txaObservaciones.setTextFormatter(Formato.getInstance().letrasFormat(1000));

        txtMontoReal.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        txtMontoFactura.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        
        txtGastoDetalle.setTextFormatter(Formato.getInstance().letrasFormat(100));
        txtGastoFactura.setTextFormatter(Formato.getInstance().letrasFormat(60));
        txtGastoMonto.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        
        
        // El número de recibo solo se habilita si el instrumento tributa
        txtNumeroRecibo.setDisable(true);
        chkTributa.selectedProperty().addListener((obs, antes, ahora) -> {
            txtNumeroRecibo.setDisable(!ahora);
            if (!ahora) {
                txtNumeroRecibo.clear();
            }
        });

        // que las demas pestañas queden desabilitadas mienstras se registra el instrumento
        habilitarTabs(false);
        inicializarTablas();

    }

    private void habilitarTabs(boolean habilitar) {
        tabPartesYpagos.setDisable(!habilitar);
        tabContable.setDisable(!habilitar);
        tabRequisitos.setDisable(!habilitar);
        tabEventos.setDisable(!habilitar);
        tabDocumentoFinal.setDisable(!habilitar);

    }

    @Override
    public void initialize() {
        revisarPermisos();
        configurarComboBoxes();
        //inicializarTablas();
    }

    private void cargarTablaRequisitos() {
        tblRequisitos.getItems().clear();
        tabRequisitos.setDisable(false);

        if (instrumentoActual == null) {
            tabRequisitos.setDisable(true);
            System.out.println("El instrumento esta vacido empty nulll");
            return;
        }

        List<InstrumentoRequisito> requisitos = instrumentoActual.getInstrumentoRequisitoList();
        if (requisitos != null) {
            tblRequisitos.setItems(FXCollections.observableArrayList(requisitos));
            System.out.println("somehow se setearon los requisitos");
        } else {
            System.out.println("Los requisitos vienen vacidos");
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
        Respuesta respuestaTipos = tipoInstrumentoService.getTipoInstrumentos(null, true, bufeteCarga());
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
        Respuesta respuestaEstados = estadoInstrumentoService.getEstadoInstrumentos(true, null, null, bufeteCarga());
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
            cmbTipoInstrumento.requestFocus();
            return;
        }

        if (abogadoTitularId == null) {
            mostrarError("Seleccione el abogado titular");
            return;
        }

        if (estado == null) {
            mostrarError("Seleccione el estado del instrumento");
            cmbEstado.requestFocus();
            return;
        }

        if (fecha == null) {
            mostrarError("Seleccione la fecha del instrumento");
            dpFechaInstrumento.requestFocus();
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
            instrumento.setInsBufeteId(bufeteCarga());
            instrumento.setInsTipoId(tipo.getTinId());
            instrumento.setInsEstadoId(estado.getEinId());
        } else { // modo editar cuando ya cargue el inst
            instrumento = instrumentoActual;
            cmbEstado.setDisable(true);
            cmbTipoInstrumento.setDisable(true);
        }

        instrumento.setInsAbogadoId(abogadoTitularId);
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
            instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
            if (instrumentoEsNuevo) {
                mostrarExito("Instrumento registrado exitosamenet");
                cargarTablaRequisitos();
                habilitarTabs(true);
                //limpiarCampos();
            } else {
                mostrarExito("Instrumento modificado exitosamenet");
                cargarTablaRequisitos();
                habilitarTabs(true);
            }
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    private void revisarPermisos() {
        if (instrumentoActual == null) {
            btnGuardarInstrumento.setDisable(!Permisos.tienePermiso("InstrumentosView", "Crear"));
        } else {
            btnGuardarInstrumento.setDisable(!Permisos.tienePermiso("InstrumentosView", "Modificar"));
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

    private Long bufeteCarga() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        return usuarioSesion.getUsrBufeteId();
    }

    private void limpiarCampos() {
        abogadoTitularId = null;
        instrumentoActual = null;

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

        cargarTablaRequisitos(); // para limpiar la tabla de requisitos
        cargarTablaGastos();
        tabPane.getSelectionModel().select(tabGeneralesDelInstrumento);
        revisarPermisos();
    }

    @FXML
    private void onActionBtnBuscarInstrumento(ActionEvent event) {
        // me falta editar un instrumento TOODOOOO
        AppContext.getInstance().delete("instrumentoSeleccionado");
        FlowController.getInstance().goViewInWindowModal("InstrumentosBusquedaView", stage, Boolean.FALSE);

        ResultadoInstrumento instrumentoSeleccionado = (ResultadoInstrumento) AppContext.getInstance().get("instrumentoSeleccionado");
        if (instrumentoSeleccionado == null) {
            return;
        }

        Respuesta respuesta = instrumentoService.getInstrumento(instrumentoSeleccionado.getRinId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
            cargarInstrumento(instrumentoActual);
            revisarPermisos();
            mostrarExito("Instrumento cargado");
        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    private void cargarInstrumento(Instrumento inst) {
        AppContext.getInstance().delete("instrumentoSeleccionado");

        cargarTablaRequisitos();
        cargarTablaGastos();
        cargarTablaEventos();
        habilitarTabs(true);
        cmbTipoInstrumento.setDisable(true);
        cmbEstado.setDisable(true);

        txtAbogadoTitular.setText(inst.getInsAbogadoNombre());

        //dpFechaInstrumento.setValue();
        //txtHoraInstrumento.setText(LocalDate.parse(inst.getInsFechaHora()));
        txtEscritura.setText(inst.getInsEscritura());

        txtTomo.setText(inst.getInsTomo());
        txtFolio.setText(inst.getInsFolio());
        txtTomoAsiento.setText(inst.getInsTomoAsiento());
        chkIndiceNotarial.setSelected(inst.getInsIndiceNotarial()); // t/f

        txaDetalleInstrumento.setText(inst.getInsDetalle());
        txaObservacionEstado.setText(inst.getInsObservaciones());

        txaObservacionEstado.setText(inst.getInsObsEstado());
        
        for(TipoInstrumento tin: cmbTipoInstrumento.getItems()){
            if(inst.getInsTipoId().equals(tin.getTinId())){
                cmbTipoInstrumento.getSelectionModel().select(tin);
            }
        }
        
        for(EstadoInstrumento estado: cmbEstado.getItems()){
            if(inst.getInsEstadoId().equals(estado.getEinId())){
                cmbEstado.getSelectionModel().select(estado);
            }
        }
        

    }

    // ===================== TAB GASTOS ========================================
    @FXML
    private void onActionBtnAgregarGasto(ActionEvent event) {

        if (instrumentoActual == null) {
            mostrarError("Primero guarde o cargue un instrumento");
            return;
        }

        String detalle = txtGastoDetalle.getText();
        LocalDate fecha = dpGastoFecha.getValue();
        BigDecimal monto = leerMonto(txtGastoMonto); // es bigdecimal
        String factura = txtGastoFactura.getText();
        Boolean tributa = chkGastoTributa.isSelected();

        if (detalle.isEmpty()) {
            mostrarError("El campo de detalle no puede estar vacido");
            txtGastoDetalle.requestFocus();
            return;
        }

        // revisar los campos que pueden ir vacidos
        if(factura.isEmpty()){
            mostrarError("El campo de factura no puede estar vacido");
            txtGastoFactura.requestFocus();
            return;
        }
        
        if (fecha == null) {
            mostrarError("Ingrese una fecha");
            dpGastoFecha.requestFocus();
            return;
        }

        if (monto == null) {
            mostrarError("El monto no es un número válido");
            txtGastoMonto.requestFocus();
            return;
        }

        InstrumentoGasto instGasto = new InstrumentoGasto(detalle, fecha, monto, factura, tributa);
        if (gastoEditando != null) {
            instGasto.setIgaId(gastoEditando.getIgaId());   // con id → el WS actualiza
        }
        instGasto.setIgaInstrumentoId(instrumentoActual.getInsId());
        Respuesta respuesta = instrumentoGastoService.guardarGasto(BufeteCargado(), instGasto);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Se guardo el gasto correctamente");
            limpiarCamposGasto();
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    private void limpiarCamposGasto() {
        gastoEditando = null;
        btnAgregarGasto.setText("Agregar");
        txtGastoDetalle.clear();
        dpGastoFecha.setValue(null);
        txtGastoFactura.clear();
        txtGastoMonto.clear();
        chkGastoTributa.setSelected(false);
        cargarTablaGastos();
    }

    @FXML
    private void onActionBtnAgregarEvento(ActionEvent event) {

        if (instrumentoActual == null) {
            mostrarError("Primero guarde o cargue un instrumento");
            return;
        }

        String detalle = txtEventoDetalle.getText().trim();
        LocalDate fecha = dpEventoFecha.getValue();
        LocalTime hora;

        if (detalle.isEmpty()) {
            mostrarError("Indique el detalle del evento");
            txtEventoDetalle.requestFocus();
            return;
        }

        if (fecha == null) {
            mostrarError("Seleccione la fecha del evento");
            dpEventoFecha.requestFocus();
            return;
        }

        try {
            hora = LocalTime.parse(txtEventoHora.getText().trim(), FORMATO_HORA);
        } catch (DateTimeParseException ex) {
            mostrarError("La hora debe tener el formato HH:mm, por ejemplo 09:30");
            txtEventoHora.requestFocus();
            return;
        }

        Evento evento = new Evento(detalle, LocalDateTime.of(fecha, hora), false);
        if (eventoEditando != null) {
            evento.setEveId(eventoEditando.getEveId());
        }
        evento.setEveInstrumentoId(instrumentoActual.getInsId());

        // un evento nuevo siempre empieza sin atender
        Respuesta respuesta = eventoService.guardarEvento(bufeteCarga(), evento);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Evento registrado correctamente");
            limpiarCamposEvento();
            cargarTablaEventos();
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    private void limpiarCamposEvento() {
        eventoEditando = null;
        btnAgregarEvento.setText("Agregar");
        txtEventoDetalle.clear();
        dpEventoFecha.setValue(null);
        txtEventoHora.clear();
    }

    private void cargarTablaEventos() {
        listaEventos.clear();
        if (instrumentoActual == null) {
            return;
        }
        Respuesta respuesta = eventoService.getEventos(bufeteCarga(), instrumentoActual.getInsId(), null, null, null);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Evento> eventos = (List<Evento>) respuesta.getResultado("Eventos");
            if (eventos != null) {
                listaEventos.setAll(eventos);
            }
        } else {
            mostrarError("Error cargando los eventos: " + respuesta.getMensaje());
        }
    }

    private void editarEvento(Evento evento) {
        eventoEditando = evento;
        txtEventoDetalle.setText(evento.getEveDetalle());
        dpEventoFecha.setValue(evento.getEveFechaHora().toLocalDate());
        txtEventoHora.setText(evento.getEveFechaHora().toLocalTime().format(FORMATO_HORA));
        btnAgregarEvento.setText("Guardar cambios");
    }

    private void eliminarEvento(Evento evento) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el evento \"" + evento.getEveDetalle() + "\"?", ButtonType.YES, ButtonType.NO);
        confirmar.setHeaderText(null);
        if (confirmar.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        Respuesta respuesta = eventoService.eliminarEvento(bufeteCarga(), evento.getEveId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Evento eliminado correctamente");
            limpiarCamposEvento();
            cargarTablaEventos();
        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    private void inicializarTablas() {

        //------------------------ Eventos ----------------------------------------------
        colEventoDetalle.setCellValueFactory(new PropertyValueFactory<>("eveDetalle"));
        colEventoFechaHora.setCellValueFactory(new PropertyValueFactory<>("eveFechaHora"));
        colEventoAtendido.setCellValueFactory(data -> {
            if (Boolean.TRUE.equals(data.getValue().getEveAtendido())) {
                return new SimpleStringProperty("Sí");
            } else {
                return new SimpleStringProperty("No");
            }
        });

        colEventoAcciones.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));
        colEventoAcciones.setCellFactory(columna -> new TableCell<Evento, Evento>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.setOnAction(e -> editarEvento(getItem()));
                btnEliminar.setOnAction(e -> eliminarEvento(getItem()));
            }

            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
        tblEventos.setPlaceholder(new Label("No hay eventos registrados"));
        tblEventos.setItems(listaEventos);

        //----------------------------- Requisitos ------------------------------------
        colNumero.setCellValueFactory(new PropertyValueFactory<>("irqOrden"));
        colRequisito.setCellValueFactory(new PropertyValueFactory<>("irqDescripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("irqEstado"));

        colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList("Cumplido", "Pendiente")));
        colEstado.setOnEditCommit(event -> {

        });

        //------------------------ Gastos ----------------------------------------------
        colGastoDetalle.setCellValueFactory(new PropertyValueFactory<>("igaDetalle"));
        colGastoFecha.setCellValueFactory(new PropertyValueFactory<>("igaFecha"));
        colGastoMonto.setCellValueFactory(new PropertyValueFactory<>("igaMonto"));
        colGastoFactura.setCellValueFactory(new PropertyValueFactory<>("igaFactura"));
        colGastoTributa.setCellValueFactory(data -> {
            if (Boolean.TRUE.equals(data.getValue().getIgaTributa())) {
                return new SimpleStringProperty("Sí");
            } else {
                return new SimpleStringProperty("No");
            }
        });
        colGastoAcciones.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));
        colGastoAcciones.setCellFactory(columna -> new TableCell<InstrumentoGasto, InstrumentoGasto>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {
                btnEditar.setOnAction(e -> editarGasto(getItem()));
                btnEliminar.setOnAction(e -> eliminarGasto(getItem()));
            }

            @Override
            protected void updateItem(InstrumentoGasto gasto, boolean empty) {
                super.updateItem(gasto, empty);
                if (empty || gasto == null) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });

        tblGastos.setPlaceholder(new Label("No hay gastos registrados por el momento"));
        tblGastos.setItems(listaGastos);

    }

    private Long BufeteCargado() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        return usuarioSesion.getUsrBufeteId();
    }

    //============== cargar tablas
    private void cargarTablaGastos() {
        listaGastos.clear();
        if (instrumentoActual == null) {
            return;
        }
        //ID buf actual
        Respuesta respuesta = instrumentoGastoService.getGastos(bufeteCarga(), instrumentoActual.getInsId(), null, null);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<InstrumentoGasto> gastos = (List<InstrumentoGasto>) respuesta.getResultado("InstrumentosGastos");
            if (gastos != null) {
                listaGastos.setAll(gastos);
            }
        } else {
            mostrarError("Error cargando los gastos: " + respuesta.getMensaje());
        }
    }

    private void editarGasto(InstrumentoGasto gasto) {
        gastoEditando = gasto;
        txtGastoDetalle.setText(gasto.getIgaDetalle());
        dpGastoFecha.setValue(gasto.getIgaFecha());
        txtGastoMonto.setText(gasto.getIgaMonto().toPlainString());
        txtGastoFactura.setText(gasto.getIgaFactura());
        chkGastoTributa.setSelected(Boolean.TRUE.equals(gasto.getIgaTributa()));
        btnAgregarGasto.setText("Guardar cambios");
    }

    private void eliminarGasto(InstrumentoGasto gasto) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el gasto \"" + gasto.getIgaDetalle() + "\"?", ButtonType.YES, ButtonType.NO);
        confirmar.setHeaderText(null);
        if (confirmar.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }

        Respuesta respuesta = instrumentoGastoService.eliminarGasto(bufeteCarga(), gasto.getIgaId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Gasto eliminado correctamente");
            limpiarCamposGasto();   // por si estaba editando ese mismo gasto
        } else {
            mostrarError(respuesta.getMensaje());
        }
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