package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.model.ArchivoDto;
import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
import cr.ac.una.proyectoprogra.model.Evento;
import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.InstrumentoGasto;
import cr.ac.una.proyectoprogra.model.InstrumentoParte;
import cr.ac.una.proyectoprogra.model.InstrumentoRequisito;
import cr.ac.una.proyectoprogra.model.PartePago;
import cr.ac.una.proyectoprogra.model.ResultadoInstrumento;
import cr.ac.una.proyectoprogra.model.SociedadDto;
import cr.ac.una.proyectoprogra.model.TipoInstRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.EstadoInstrumentoService;
import cr.ac.una.proyectoprogra.service.EventoService;
import cr.ac.una.proyectoprogra.service.InstrumentoGastoService;
import cr.ac.una.proyectoprogra.service.InstrumentoParteService;
import cr.ac.una.proyectoprogra.service.InstrumentoService;
import cr.ac.una.proyectoprogra.service.PartePagoService;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Permisos;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Base64;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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

    // ======= PARTES Y PAGOS
    private MFXTextField txtCliente;
    @FXML
    private TextField txtPartesMonto;
    @FXML
    private ComboBox<String> cmbPartesRol;
    @FXML
    private CheckBox chkPartesDebePagar;
    @FXML
    private TableView<InstrumentoParte> tblPartes;
    @FXML
    private TableColumn<InstrumentoParte, String> colPartesSeleccionado;
    @FXML
    private TableColumn<InstrumentoParte, String> colPartesTipo;
    @FXML
    private TableColumn<InstrumentoParte, String> colPartesRol;
    @FXML
    private TableColumn<InstrumentoParte, String> colPartesPaga;
    @FXML
    private TableColumn<InstrumentoParte, BigDecimal> colPartesMonto;
    @FXML
    private TableColumn<InstrumentoParte, BigDecimal> colPartesSaldo;
    @FXML
    private TableColumn<InstrumentoParte, InstrumentoParte> colPartesAcciones;
    @FXML
    private Label lblSaldoInstrumento;

    private InstrumentoParteService instrumentoParteService = new InstrumentoParteService();
    private ObservableList<InstrumentoParte> listaPartes = FXCollections.observableArrayList();
    private Long parteClienteId;      // el elegido en la busqeuda
    private Long parteSociedadId;       // cualquiera de los dos
    private InstrumentoParte parteEditando;
    private final String[] filtroPartesRol = {"CEDENTE", "CESIONARIO"};
    @FXML
    private RadioButton rdbCliente;
    @FXML
    private RadioButton rdbSociedad;
    @FXML
    private ToggleGroup grupoTipoParte;

    @FXML
    private TableView<PartePago> tblPagos;

    @FXML
    private MFXTextField txtPartesSeleccionada;
    @FXML
    private Button btnAgregarParte;
    @FXML
    private TextField txtPagoDetalle;
    @FXML
    private MFXDatePicker dpPagoFecha;
    @FXML
    private TextField txtPagoFactura;
    @FXML
    private TextField txtPagoMonto;
    @FXML
    private TableColumn<PartePago, String> colPagoDetalle;
    @FXML
    private TableColumn<PartePago, LocalDate> colPagoFecha;
    @FXML
    private TableColumn<PartePago, String> colPagoFactura;
    @FXML
    private TableColumn<PartePago, BigDecimal> colPagoMonto;
    @FXML
    private TableColumn<PartePago, PartePago> colPagoAcciones;
    private PartePagoService partePagoService = new PartePagoService();
    private ObservableList<PartePago> listaPagos = FXCollections.observableArrayList();

    @FXML
    private Label lblSaldoParte;
    @FXML
    private Button btnRegistrarPago;
    @FXML
    private VBox vboxElegirParte;
    @FXML
    private Button btnBuscarParte;
    @FXML
    private Button btnDescargarDocumento;
    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Button btnSubirDocuFirmado;
    @FXML
    private Label lblNombreDocuFirmado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtHoraInstrumento.setTextFormatter(Formato.getInstance().maxLengthFormat(5));
        txtNumeroBoleta.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtEscritura.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtTomoAsiento.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtFolio.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtTomo.setTextFormatter(Formato.getInstance().letrasFormat(20));
        txtNumeroRecibo.setTextFormatter(Formato.getInstance().maxLengthFormat(30));

        txaObservacionEstado.setTextFormatter(Formato.getInstance().letrasFormat(500));
        txaDetalleInstrumento.setTextFormatter(Formato.getInstance().letrasFormat(200));
        txaObservaciones.setTextFormatter(Formato.getInstance().letrasFormat(1000));

        txtMontoReal.setTextFormatter(Formato.getInstance().maxLengthFormat(15));
        txtMontoFactura.setTextFormatter(Formato.getInstance().maxLengthFormat(15));

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

        cmbPartesRol.getItems().setAll(filtroPartesRol);
        cmbPartesRol.getSelectionModel().selectFirst();

        // el monto solo se habilita si la parte paga
        txtPartesMonto.setDisable(true);
        chkPartesDebePagar.selectedProperty().addListener((obs, antes, ahora) -> {
            txtPartesMonto.setDisable(!ahora);
            if (!ahora) {
                txtPartesMonto.clear();
            }
        });

        // si cambia entre físico y jurídico, se borra lo elegido
        grupoTipoParte.selectedToggleProperty().addListener((obs, antes, ahora) -> {
            parteClienteId = null;
            parteSociedadId = null;
            txtPartesSeleccionada.clear();
        });
        rdbCliente.setSelected(true);

        btnRegistrarPago.setDisable(true);
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

        tblRequisitos.setEditable(true);

        colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String letra) {
                if ("C".equals(letra)) {
                    return "Cumplido";
                } else if ("P".equals(letra)) {
                    return "Pendiente";
                } else if ("N".equals(letra)) {
                    return "No aplica";
                }
                return "";
            }

            @Override
            public String fromString(String texto) {
                if ("Cumplido".equals(texto)) {
                    return "C";
                } else if ("Pendiente".equals(texto)) {
                    return "P";
                }
                return "N";
            }
        }, "C", "P", "N"));

        // cuando el usuario elige otra opción, se guarda de una vez
        colEstado.setOnEditCommit(evento -> {
            InstrumentoRequisito requisito = evento.getRowValue();
            String estadoAnterior = evento.getOldValue();
            requisito.setIrqEstado(evento.getNewValue());

            Respuesta respuesta = instrumentoService.actualizarRequisitos(instrumentoActual.getInsId(), bufeteCarga(), List.of(requisito));
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
                cargarTablaRequisitos();
            } else {
                requisito.setIrqEstado(estadoAnterior);   // si falló, vuelve a como estaba
                tblRequisitos.refresh();
                mostrarError(respuesta.getMensaje());
            }
        });

//---------------------------- Gastos ----------------------------------------------
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

                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));
                iconoEliminar.setFitHeight(30);
                iconoEliminar.setFitWidth(30);

                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);

                btnEliminar.setGraphic(iconoEliminar);
                btnEditar.setGraphic(iconoEditar);

                btnEliminar.setMinWidth(30);
                btnEditar.setMinWidth(30);

                btnEliminar.setStyle("-fx-background-color: transparent;");
                btnEditar.setStyle("-fx-background-color: transparent;");

                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(10);

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

        //------------------------ Partes ----------------------------------------------
        colPartesSeleccionado.setCellValueFactory(new PropertyValueFactory<>("ipaNombre"));
        colPartesRol.setCellValueFactory(new PropertyValueFactory<>("ipaRol"));
        colPartesMonto.setCellValueFactory(new PropertyValueFactory<>("ipaMonto"));
        colPartesSaldo.setCellValueFactory(new PropertyValueFactory<>("ipaSaldo"));
        colPartesTipo.setCellValueFactory(data -> {
            if (data.getValue().getIpaClienteId() != null) {
                return new SimpleStringProperty("Físico");
            } else {
                return new SimpleStringProperty("Jurídico");
            }
        });
        colPartesPaga.setCellValueFactory(data -> {
            if (Boolean.TRUE.equals(data.getValue().getIpaDebePagar())) {
                return new SimpleStringProperty("Sí");
            } else {
                return new SimpleStringProperty("No");
            }
        });
        colPartesAcciones.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));
        colPartesAcciones.setCellFactory(columna -> new TableCell<InstrumentoParte, InstrumentoParte>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");
            private final HBox contenedor = new HBox(5, btnEditar, btnEliminar);

            {

                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));
                iconoEliminar.setFitHeight(30);
                iconoEliminar.setFitWidth(30);

                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);

                btnEliminar.setGraphic(iconoEliminar);
                btnEditar.setGraphic(iconoEditar);

                btnEliminar.setMinWidth(30);
                btnEditar.setMinWidth(30);

                btnEliminar.setStyle("-fx-background-color: transparent;");
                btnEditar.setStyle("-fx-background-color: transparent;");

                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(10);

                btnEditar.setOnAction(e -> editarParte(getItem()));
                btnEliminar.setOnAction(e -> eliminarParte(getItem()));
            }

            @Override
            protected void updateItem(InstrumentoParte parte, boolean empty) {
                super.updateItem(parte, empty);
                if (empty || parte == null) {
                    setGraphic(null);
                } else {
                    setGraphic(contenedor);
                }
            }
        });
        tblPartes.setPlaceholder(new Label("No hay partes registradas"));
        tblPartes.setItems(listaPartes);

        //------------------------ Pagos ----------------------------------------------
        colPagoDetalle.setCellValueFactory(new PropertyValueFactory<>("ppaDetalle"));
        colPagoFecha.setCellValueFactory(new PropertyValueFactory<>("ppaFecha"));
        colPagoFactura.setCellValueFactory(new PropertyValueFactory<>("ppaFactura"));
        colPagoMonto.setCellValueFactory(new PropertyValueFactory<>("ppaMonto"));
        colPagoAcciones.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue()));
        colPagoAcciones.setCellFactory(columna -> new TableCell<PartePago, PartePago>() {
            private final Button btnEliminar = new Button("Eliminar");

            {

                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));
                iconoEliminar.setFitHeight(30);
                iconoEliminar.setFitWidth(30);
                btnEliminar.setGraphic(iconoEliminar);
                btnEliminar.setMinWidth(30);
                btnEliminar.setStyle("-fx-background-color: transparent;");

                btnEliminar.setOnAction(e -> eliminarPago(getItem()));
            }

            @Override
            protected void updateItem(PartePago pago, boolean empty) {
                super.updateItem(pago, empty);
                if (empty || pago == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });
        tblPagos.setPlaceholder(new Label("Seleccione una parte para ver sus pagos"));
        tblPagos.setItems(listaPagos);

        // al seleccionar una parte, abajo se muestran sus pagos
        tblPartes.getSelectionModel().selectedItemProperty().addListener((obs, antes, ahora) -> mostrarPagos(ahora));

    }

    @Override
    public void initialize() {
        configurarComboBoxes();

        Long instrumentoId = (Long) AppContext.getInstance().get("instrumentoSeleccionado");
        if (instrumentoId != null) {

            Respuesta respuesta = instrumentoService.getInstrumento(instrumentoId);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
                cargarInstrumento(instrumentoActual);
            } else {
                mostrarError("Error cargando el instrumento: " + respuesta.getMensaje());
            }
        } else if (instrumentoActual != null) {
            cargarInstrumento(instrumentoActual);
        }

        revisarPermisos();
    }

    // =================== REQUISITOS ==============================
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

        if (observacionEstado == null) {
            System.out.println("La observacion del estado viene vacido");
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
        } else { // modo editar cuando ya cargue el inst
            instrumento = instrumentoActual;
            if (instrumentoActual.getInsFechaEstado() != null) {
                txtMostrarFechaEstado.setText(instrumentoActual.getInsFechaEstado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }
            cmbTipoInstrumento.setDisable(true);

        }

        instrumento.setInsAbogadoId(abogadoTitularId);
        instrumento.setInsEstadoId(estado.getEinId());
        instrumento.setInsObsEstado(txaObservacionEstado.getText().trim());
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
                limpiarCampos();
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
        cmbTipoInstrumento.setDisable(false);
        cmbEstado.setDisable(false);
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
        cargarTablaEventos();
        cargarTablaPartes();

        limpiarCamposGasto();     // sale de modo edicion y recarga la tabla
        limpiarCamposEvento();
        cargarTablaEventos();
        limpiarCamposParte();     // igual
        limpiarCamposPago();

        habilitarTabs(false);
        tabPane.getSelectionModel().select(tabGeneralesDelInstrumento);
        revisarPermisos();
    }

    @FXML
    private void onActionBtnBuscarInstrumento(ActionEvent event) {

        AppContext.getInstance().delete("instrumentoSeleccionado");
        AppContext.getInstance().set("busquedaModal", true);
        FlowController.getInstance().limpiarLoader("InstrumentosBusquedaView");
        FlowController.getInstance().goViewInWindowModal("InstrumentosBusquedaView", stage, Boolean.FALSE);
        AppContext.getInstance().delete("busquedaModal");

        Long instrumentoId = (Long) AppContext.getInstance().get("instrumentoSeleccionado");
        if (instrumentoId == null) {
            return;
        }

        Respuesta respuesta = instrumentoService.getInstrumento(instrumentoId);
        if (respuesta.getEstado()) {
            instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
            cargarInstrumento(instrumentoActual);
            revisarPermisos();
            mostrarExito("Instrumento cargado");
        } else {
            mostrarError("Error cargando el instrumento: " + respuesta.getMensaje());
        }
    }

    private void cargarInstrumento(Instrumento inst) {
        AppContext.getInstance().delete("instrumentoSeleccionado");
        abogadoTitularId = inst.getInsAbogadoId();

        for (int i = 0; i < cmbTipoInstrumento.getItems().size(); i++) {
            if (cmbTipoInstrumento.getItems().get(i).getTinId().equals(inst.getInsTipoId())) {
                cmbTipoInstrumento.getSelectionModel().select(i);
            }
        }
        for (int i = 0; i < cmbEstado.getItems().size(); i++) {
            if (cmbEstado.getItems().get(i).getEinId().equals(inst.getInsEstadoId())) {
                cmbEstado.getSelectionModel().select(i);
            }
        }
        cmbTipoInstrumento.setDisable(true);

        txtAbogadoTitular.setText(inst.getInsAbogadoNombre());

        dpFechaInstrumento.setValue(inst.getInsFechaHora().toLocalDate());
        txtHoraInstrumento.setText(inst.getInsFechaHora().toLocalTime().format(FORMATO_HORA));
        if (inst.getInsFechaEstado() != null) {
            txtMostrarFechaEstado.setText(inst.getInsFechaEstado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }
        txtNumeroBoleta.setText(inst.getInsBoleta());
        txtEscritura.setText(inst.getInsEscritura());
        txtTomo.setText(inst.getInsTomo());
        txtFolio.setText(inst.getInsFolio());
        txtTomoAsiento.setText(inst.getInsTomoAsiento());
        chkIndiceNotarial.setSelected(Boolean.TRUE.equals(inst.getInsIndiceNotarial()));

        txaObservacionEstado.setText(inst.getInsObsEstado());
        txaDetalleInstrumento.setText(inst.getInsDetalle());
        txaObservaciones.setText(inst.getInsObservaciones());

        chkTributa.setSelected(Boolean.TRUE.equals(inst.getInsTributa()));
        txtNumeroRecibo.setText(inst.getInsNumeroRecibo());
        txtMontoReal.setText(inst.getInsMontoReal().toPlainString());
        txtMontoFactura.setText(inst.getInsMontoFacturado().toPlainString());

        cargarTablaRequisitos();
        cargarTablaGastos();
        cargarTablaEventos();
        cargarTablaPartes();
        habilitarTabs(true);

        boolean esFinal = Boolean.TRUE.equals(inst.getInsEstadoFinal());
        btnGuardarInstrumento.setDisable(esFinal);
        vboxElegirParte.setDisable(esFinal);
        btnBuscarParte.setDisable(esFinal);
        btnAgregarParte.setDisable(esFinal);
        tblRequisitos.setEditable(!esFinal);
        
        
        if(inst.getInsRutaDocFirmado() == null || inst.getInsRutaDocFirmado().isBlank()){
            lblNombreDocuFirmado.setText("No se ha subido el documento firmado");
        }else{
            lblNombreDocuFirmado.setText(inst.getInsRutaDocFirmado());
            
        }
        
        if(inst.getInsRutaDocumento() == null || inst.getInsRutaDocumento().isBlank()){
            lblNombreDocumento.setText("No se ha generado el documento");
        }else{
            lblNombreDocumento.setText(inst.getInsRutaDocumento());
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
        if (factura.isEmpty()) {
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
        Respuesta respuesta = instrumentoGastoService.guardarGasto(bufeteCarga(), instGasto);
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

    // ================== TAB EVENTOS ========================================
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

    // ==================== PARTES ================
    @FXML
    private void onActionBtnAgregarPartes(ActionEvent event) {

        if (instrumentoActual == null) {
            mostrarError("Necesita guardar o cargar un instrumento primero"); // otra validacion que realmente es por si acaso, ya que los tabs estan desactivados entonces no se puede intentar hacer nada en otra parte
            return;
        }

        if (parteClienteId == null && parteSociedadId == null) {
            mostrarError("Seleccione una sociedad o un cliente");
            rdbCliente.requestFocus();
            return;
        }

        String rol = cmbPartesRol.getValue();
        if (rol == null) {
            mostrarError("Seleccione el papel de la parte");
            cmbPartesRol.requestFocus();
            return;
        }
        boolean debePagar = chkPartesDebePagar.isSelected();
        BigDecimal montoApagar = BigDecimal.ZERO;

        if (debePagar) {
            montoApagar = leerMonto(txtPartesMonto);
            if (montoApagar == null || montoApagar.compareTo(BigDecimal.ZERO) <= 0) {
                mostrarError("Si la parte paga, el monto debe ser mayor a cero");
                txtPartesMonto.requestFocus();
                return;
            }
        }

        InstrumentoParte instParte = new InstrumentoParte(instrumentoActual.getInsId(), parteClienteId, parteSociedadId, rol, debePagar, montoApagar);
        if (parteEditando != null) {
            instParte.setIpaId(parteEditando.getIpaId());   // con id el WS actualiza
        }
        Respuesta respuesta = instrumentoParteService.guardarParte(bufeteCarga(), instParte);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Parte guardada correctamente");
            limpiarCamposParte();
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    @FXML
    private void onActionBtnBuscarParteSeleccionada(ActionEvent event) {
        if (rdbCliente.isSelected()) {
            AppContext.getInstance().delete("clienteSeleccionado");
            FlowController.getInstance().goViewInWindowModal("BuscarClienteView", stage, Boolean.FALSE);

            Cliente cliente = (Cliente) AppContext.getInstance().get("clienteSeleccionado");
            if (cliente != null) {
                parteClienteId = cliente.getCliId();
                parteSociedadId = null;
                txtPartesSeleccionada.setText(cliente.getCliNombre() + " " + cliente.getCliApellido1() + " " + cliente.getCliApellido2());
                AppContext.getInstance().delete("clienteSeleccionado");
            }
        } else {
            AppContext.getInstance().delete("sociedadSeleccionada");
            FlowController.getInstance().goViewInWindowModal("BusquedaSociedadView", stage, Boolean.FALSE);

            SociedadDto sociedad = (SociedadDto) AppContext.getInstance().get("sociedadSeleccionada");
            if (sociedad != null) {
                parteSociedadId = sociedad.getSocId();
                parteClienteId = null;
                txtPartesSeleccionada.setText(sociedad.getSocNombre());
                AppContext.getInstance().delete("sociedadSeleccionada");
            }
        }
    }

    private void editarParte(InstrumentoParte parte) {
        vboxElegirParte.setDisable(true);
        btnBuscarParte.setDisable(true);
        txtPartesSeleccionada.setDisable(true);

        if (parte.getIpaClienteId() != null) {
            rdbCliente.setSelected(true);
        } else {
            rdbSociedad.setSelected(true);
        }

        parteEditando = parte;
        parteClienteId = parte.getIpaClienteId();
        parteSociedadId = parte.getIpaSociedadId();
        txtPartesSeleccionada.setText(parte.getIpaNombre());
        cmbPartesRol.setValue(parte.getIpaRol());
        chkPartesDebePagar.setSelected(Boolean.TRUE.equals(parte.getIpaDebePagar()));
        txtPartesMonto.setText(parte.getIpaMonto().toPlainString());
        btnAgregarParte.setText("Guardar Cambios");

    }

    private void eliminarParte(InstrumentoParte parte) {
        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar la parte \"" + parte.getIpaNombre() + "\"?", ButtonType.YES, ButtonType.NO);
        confirmar.setHeaderText(null);
        if (confirmar.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }
        Respuesta respuesta = instrumentoParteService.eliminarParte(bufeteCarga(), parte.getIpaId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Parte eliminada correctamente");
            limpiarCamposParte();
        } else {
            mostrarError(respuesta.getMensaje());   // ej: "La parte ya tiene pagos registrados..."
        }
    }

    private void limpiarCamposParte() {
        vboxElegirParte.setDisable(false);
        btnBuscarParte.setDisable(false);
        txtPartesSeleccionada.setDisable(false);

        parteEditando = null;
        btnAgregarParte.setText("Agregar parte");
        rdbCliente.setSelected(true);
        parteClienteId = null;
        parteSociedadId = null;
        txtPartesSeleccionada.clear();
        cmbPartesRol.getSelectionModel().clearSelection();
        chkPartesDebePagar.setSelected(false);
        cargarTablaPartes();
    }

    private void cargarTablaPartes() {
        listaPartes.clear();
        lblSaldoInstrumento.setText("₡0");
        if (instrumentoActual == null) {
            return;
        }
        Respuesta respuesta = instrumentoParteService.getPartes(bufeteCarga(), instrumentoActual.getInsId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<InstrumentoParte> partes = (List<InstrumentoParte>) respuesta.getResultado("Partes");
            if (partes != null) {
                listaPartes.setAll(partes);
            }
            BigDecimal saldo = BigDecimal.ZERO;
            for (InstrumentoParte p : listaPartes) {
                saldo = saldo.add(p.getIpaSaldo());
            }
            lblSaldoInstrumento.setText("₡" + saldo.toPlainString());
        } else {
            mostrarError("Error cargando las partes: " + respuesta.getMensaje());
        }
    }

    // ======================== PAGOS ===============================
    @FXML
    private void onActionBtnRegistrarPago(ActionEvent event) {
        InstrumentoParte parte = tblPartes.getSelectionModel().getSelectedItem();
        if (parte == null) {
            mostrarError("Seleccione en la tabla de arriba la parte que realiza el pago");
            return;
        }

        String detalle = txtPagoDetalle.getText().trim();
        LocalDate fecha = dpPagoFecha.getValue();
        BigDecimal monto = leerMonto(txtPagoMonto);

        if (detalle.isEmpty()) {
            mostrarError("Indique el detalle del pago");
            txtPagoDetalle.requestFocus();
            return;
        }
        if (fecha == null) {
            mostrarError("Seleccione la fecha del pago");
            dpPagoFecha.requestFocus();
            return;
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            mostrarError("El monto del pago debe ser mayor a cero");
            txtPagoMonto.requestFocus();
            return;
        }

        if (monto.compareTo(parte.getIpaSaldo()) > 0) {
            mostrarError("El pago no puede ser mayor al saldo de la parte (₡" + parte.getIpaSaldo().toPlainString() + ")");
            txtPagoMonto.requestFocus();
            return;
        }

        PartePago pago = new PartePago(parte.getIpaId(), detalle, fecha, txtPagoFactura.getText().trim(), monto);
        Respuesta respuesta = partePagoService.registrarPago(bufeteCarga(), pago);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Pago registrado correctamente");
            limpiarCamposPago();
            recargarPartesManteniendoSeleccion(parte.getIpaId());
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    private void eliminarPago(PartePago pago) {

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea eliminar el pago \"" + pago.getPpaDetalle() + "\"?", ButtonType.YES, ButtonType.NO);
        confirmar.setHeaderText(null);
        if (confirmar.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }

        Respuesta respuesta = partePagoService.eliminarPago(bufeteCarga(), pago.getPpaId());
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Pago eliminado con éxito");
            recargarPartesManteniendoSeleccion(pago.getPpaParteId());
        } else {
            mostrarError(respuesta.getMensaje());
        }

    }

    private void recargarPartesManteniendoSeleccion(Long idPartePago) {
        cargarTablaPartes();
        for (InstrumentoParte p : listaPartes) {
            if (p.getIpaId().equals(idPartePago)) {
                tblPartes.getSelectionModel().select(p);
            }
        }
    }

    private void mostrarPagos(InstrumentoParte parte) {

        listaPagos.clear();

        if (parte == null) {
            lblSaldoParte.setText("₡0");
            btnRegistrarPago.setDisable(true);
            return;
        }

        if (parte.getPartePagoList() != null) {
            listaPagos.setAll(parte.getPartePagoList());
        }

        lblSaldoParte.setText("₡" + parte.getIpaSaldo().toPlainString());
        // solo se puede pagar si la parte paga y le queda saldo
        btnRegistrarPago.setDisable(!Boolean.TRUE.equals(parte.getIpaDebePagar()) || parte.getIpaSaldo().compareTo(BigDecimal.ZERO) <= 0);

    }

    private void limpiarCamposPago() {
        txtPagoDetalle.clear();
        dpPagoFecha.setValue(null);
        txtPagoFactura.clear();
        txtPagoMonto.clear();
    }

    @FXML
    private void onActionBtnDescargarDocumento(ActionEvent event) {

        Respuesta respuesta = instrumentoService.generarDocumento(instrumentoActual.getInsId(), bufeteCarga());
        if (!Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarError(respuesta.getMensaje());
            return;
        }

        ArchivoDto archivo = (ArchivoDto) respuesta.getResultado("Documento");
        instrumentoActual.setInsRutaDocumento(archivo.getNombreArchivo());
        lblNombreDocumento.setText(archivo.getNombreArchivo());

        try {
            byte[] bytes = Base64.getDecoder().decode(archivo.getContenido());
            Path temp = Files.createTempFile("instrumento_", ".docx");
            Files.write(temp, bytes);
            Desktop.getDesktop().open(temp.toFile());

        } catch (IOException ex) {
            mostrarError("El documento se genero pero no se pudo abrir: " + ex.getMessage());
        }
    }

    @FXML
    private void onActionBtnSubirDocFirmado(ActionEvent event) {

        if (instrumentoActual == null) {
            mostrarError("Primero guarde o cargue un instrumento");
            return;
        }

        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccione el documento firmado");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documentos PDF", "*.pdf"));
        File seleccionado = selector.showOpenDialog(getStage());
        if (seleccionado == null) {
            return;
        }
        
        try{
            byte[] bytes = Files.readAllBytes(seleccionado.toPath());   
            String contenido = Base64.getEncoder().encodeToString(bytes);
            ArchivoDto archivo = new ArchivoDto(seleccionado.getName(), contenido);
            
            Respuesta respuesta = instrumentoService.subirFirmado(instrumentoActual.getInsId(), bufeteCarga(), archivo);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                
                instrumentoActual = (Instrumento) respuesta.getResultado("Instrumento");
                lblNombreDocuFirmado.setText(instrumentoActual.getInsRutaDocFirmado());
                mostrarExito("Documento firmado guardado correctamente");
                
            }else{
                mostrarError(respuesta.getMensaje());
            }
        }catch(IOException ex){
            mostrarError("No se pudo leer el archivo " + ex.getMessage());
        }

    }

}
