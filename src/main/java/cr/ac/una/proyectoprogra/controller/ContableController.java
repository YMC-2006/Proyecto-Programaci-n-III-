/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.MovimientoDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.MovimientoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ContableController extends Controller implements Initializable {

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
    private TextField txtDetalle;
    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox<String> cbxTipo;
    @FXML
    private MFXCheckbox chkTributa;
    @FXML
    private TableView<MovimientoDto> tblMovimientos;
    @FXML
    private TableColumn<MovimientoDto, MovimientoDto> colAcciones;
    @FXML
    private MFXButton btnAgregarMovimiento;
    @FXML
    private TableColumn<MovimientoDto, LocalDate> colFechaM;
    @FXML
    private TableColumn<MovimientoDto, String> colTipoM;
    @FXML
    private TableColumn<MovimientoDto, String> colFacturaM;
    @FXML
    private TableColumn<MovimientoDto, String> colTributaM;
    @FXML
    private TableColumn<MovimientoDto, BigDecimal> colMontoM;
    @FXML
    private TableColumn<MovimientoDto, String> colDetalleM;
    
    private List<MovimientoDto> todosLosMovimientos = new ArrayList<>();
    private ObservableList<MovimientoDto> listaMovimientos = FXCollections.observableArrayList();
    
    private final String[] filtroTipo = {"Todos", "Ingresos", "Gastos"};
    
    private MovimientoService movimientoService = new MovimientoService();
    @FXML
    private Tab tabMovimientos;
    @FXML
    private TextField txtFactura;
    @FXML
    private MFXDatePicker dtpFechaFin;
    @FXML
    private TabPane tpContable;
    @FXML
    private Tab tabContable;
    @FXML
    private MFXDatePicker dtpFechaInicio;
    @FXML
    private Label lbIngresos;
    @FXML
    private Label lbGastos;
    @FXML
    private Label lbUtilidad;
    @FXML
    private Label lbTributadas;
    @FXML
    private ComboBox<?> cbxTipoC;
    @FXML
    private TextField txtFacturaC;
    @FXML
    private MFXCheckbox chkTributaC;
    @FXML
    private TextField txtMontoC;
    @FXML
    private TextField txtDetalleC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxTipo.getItems().setAll(filtroTipo);
        cbxTipo.getSelectionModel().selectFirst();
        cbxTipo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue)->{
            filtrarMovimientos();
        });
        dtpFechaInicio.valueProperty().addListener((observable, oldValue, newValue)->{
            filtrarMovimientos();
        });
        dtpFechaFin.valueProperty().addListener((observable, oldValue, newValue)->{
            filtrarMovimientos();
        });
        inicializarMovimientos();
    }    

    @Override
    public void initialize() {

    }
    
    private void inicializarMovimientos(){
        colAcciones.setCellFactory(col -> new TableCell<MovimientoDto, MovimientoDto>() {
 
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();
            private final HBox contenedor = new HBox();
 
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
 
                btnEliminar.setOnAction(e -> {
                    MovimientoDto movimiento = getItem();
                    eliminarMovimiento(movimiento);
                });
 
                btnEditar.setOnAction(e -> {
                    MovimientoDto movimiento = getItem();
                    modificarMovimiento(movimiento);
                });
            }
 
            @Override
            protected void updateItem(MovimientoDto movimiento, boolean empty) {
                super.updateItem(movimiento, empty);
 
                if (empty || movimiento == null) {
                    setGraphic(null);
                    return;
                }
 
                contenedor.getChildren().clear();
                contenedor.getChildren().add(btnEliminar);
                contenedor.getChildren().add(btnEditar);
                setGraphic(contenedor);
            }
 
        });
 
        tblMovimientos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
 
        colFechaM.setCellValueFactory(new PropertyValueFactory<>("movFecha"));
        colTipoM.setCellValueFactory(new PropertyValueFactory<>("movTipo"));
        colFacturaM.setCellValueFactory(new PropertyValueFactory<>("movFactura"));
        colTributaM.setCellValueFactory(cellData->{
            SimpleStringProperty tributa = new SimpleStringProperty();
            if(cellData.getValue().getMovTributa() == true){
                tributa.set("Sí");
            }else{
                tributa.set("No");
            }
            return tributa;
        });
        colDetalleM.setCellValueFactory(new PropertyValueFactory<>("movDetalle"));
        colMontoM.setCellValueFactory(new PropertyValueFactory<>("movMonto"));
 
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
 
        this.tblMovimientos.setPlaceholder(new Label("No hay movimientos registrados por el momento"));
 
        tblMovimientos.setItems(listaMovimientos);
        cargarTablaMovimientos();
    }
    
    private void eliminarMovimiento(MovimientoDto mov) {
 
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar el movimiento permanentemente?");
 
        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);
 
        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);
 
        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);
 
        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();
 
        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            Respuesta respuesta = movimientoService.eliminarMovimiento(mov.getMovId());
 
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
 
                mostrarExito("Se elimino correctamente el movimiento");
                cargarTablaMovimientos();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }
 
    }
    
    private void modificarMovimiento(MovimientoDto mov) {
        AppContext.getInstance().set("movimientoEditar", mov);
        FlowController.getInstance().goViewInWindowModal("AgregarMovimientoView", stage, false);
        cargarTablaMovimientos();
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
    
    private void cargarTablaMovimientos() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = movimientoService.getMovimientos(bufeteId);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<MovimientoDto> movimientosService = (List<MovimientoDto>) respuesta.getResultado("Movimientos");
            todosLosMovimientos = movimientosService;
            filtrarMovimientos();
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Movimientos", "Error cargando los movimientos desde el service " + respuesta.getMensaje());
        }
 
    }
    
    private Predicate<MovimientoDto> filtrarFechas(){
        LocalDate fecIni = dtpFechaInicio.getValue();
        LocalDate fecFin = dtpFechaFin.getValue();
        return m->{
            LocalDate fec = m.getMovFecha();
            if(fec == null){
                return false;
            }
            if(fecIni != null && fec.isBefore(fecIni)){
                return false;
            }
            if(fecFin != null && fec.isAfter(fecFin)){
                return false;
            }
            return true;
        };
    }
    
    private void filtrarMovimientos() {
        //filtrar por fecha
        String detalle = texto(txtDetalle);
        String monto = texto(txtMonto);
        String factura = texto(txtFactura);
        Boolean tributa = chkTributa.isSelected();
        String tipo = filtroTipo();
 
        List<MovimientoDto> filtrados = todosLosMovimientos.stream()
                .filter(c -> contiene(c.getMovDetalle(), detalle))
                .filter(c -> contiene(c.getMovMonto().toString(), monto))
                .filter(c -> contiene(c.getMovFactura(), factura))
                .filter(c -> !tributa || Boolean.TRUE.equals(c.getMovTributa()))
                .filter(c -> tipo == null || c.getMovTipo().equals(tipo))
                .filter(filtrarFechas()).collect(Collectors.toList());
 
        listaMovimientos.setAll(filtrados);
    }
    
    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }
        return dato != null && dato.toLowerCase().contains(buscado);
    }

    private String texto(TextField txt) {
        return txt.getText() == null ? "" : txt.getText().trim().toLowerCase();
    }
    
    private String filtroTipo(){
        int index = cbxTipo.getSelectionModel().getSelectedIndex();
        return index == 1 ? "I" : (index == 2 ? "G" : null);
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrarMovimientos();
    }

    @FXML
    private void onActionBtnAgregarMovimiento(ActionEvent event) {
        AppContext.getInstance().delete("movimientoEditar");
        FlowController.getInstance().goViewInWindowModal("AgregarMovimientoView", stage, false);
        cargarTablaMovimientos();
    }

    @FXML
    private void onActionSelectedTributa(ActionEvent event) {
        filtrarMovimientos();
    }

    @FXML
    private void onSelectionChangedTab(Event event) {
        dtpFechaInicio.clear();
        dtpFechaFin.clear();
        filtrarMovimientos();
    }

    @FXML
    private void onActionSelectedTributaContable(ActionEvent event) {
    }
    
}
