/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.EstadoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Permisos;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class InstrumentosEstadoController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TextField txtBuscarDescripcion;
    @FXML
    private ComboBox<String> cmbFiltroEstado;
    @FXML
    private ComboBox<String> cmbFiltroNotifica;
    @FXML
    private TableView<EstadoInstrumento> tblEstadosInstrumento;
    @FXML
    private TableColumn<EstadoInstrumento, String> colDescripcion;
    @FXML
    private TableColumn<EstadoInstrumento, Boolean> colEstado;
    @FXML
    private TableColumn<EstadoInstrumento, String> colNotifica;
    @FXML
    private TableColumn<EstadoInstrumento, String> colEstadoFinal;
    @FXML
    private TableColumn<EstadoInstrumento, EstadoInstrumento> colAcciones;
    @FXML
    private MFXButton btnLimpiar;
    // TRADUCIR LATER ON 
    private final String[] filtroEstado = {"Todos", "Activo", "Inactivo"};
    private final String[] filtroNotifica = {"Todos", "Notifica", "No notifica"};
    // private final String[] filtroEstadoFinal = {"Todos", "Estado Final", ""};

    private ObservableList<EstadoInstrumento> listaEstadosIntrumento = FXCollections.observableArrayList();
    private List<EstadoInstrumento> todosLosEstados = new ArrayList<>();
    private EstadoInstrumentoService estadoInstrumentoService = new EstadoInstrumentoService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbFiltroEstado.getItems().setAll(filtroEstado);
        cmbFiltroEstado.getSelectionModel().selectFirst();

        cmbFiltroNotifica.getItems().setAll(filtroNotifica);
        cmbFiltroNotifica.getSelectionModel().selectFirst();

        colAcciones.setCellFactory(col -> new TableCell<EstadoInstrumento, EstadoInstrumento>() {

            private final Button btnEditar = new Button();
            private final HBox contenedor = new HBox();

            {
                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);

                btnEditar.setGraphic(iconoEditar);
                btnEditar.setMinWidth(30);
                btnEditar.setStyle("-fx-background-color: transparent;");
                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(5);

                btnEditar.setOnAction(e -> {
                    EstadoInstrumento estadoInstrumento = getItem();
                    modificarEstadoInstrumento(estadoInstrumento);
                });
            }

            @Override
            protected void updateItem(EstadoInstrumento estadoInstrumento, boolean vacido) {
                super.updateItem(estadoInstrumento, vacido);

                if (vacido || estadoInstrumento == null) {
                    setGraphic(null);
                    return;
                }

                contenedor.getChildren().clear();
                if (Permisos.tienePermiso("InstrumentosEstadoView", "Modificar")) {
                    contenedor.getChildren().add(btnEditar);
                }
                setGraphic(contenedor);
            }

        });

        // how did you knew you had to use new PropertyValueFactory
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("einDescripcion"));

        colEstado.setCellFactory(col -> new TableCell<EstadoInstrumento, Boolean>() {
            @Override
            protected void updateItem(Boolean activo, boolean vacido) {
                super.updateItem(activo, vacido);

                if (vacido || activo == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                Label chip = new Label();
                chip.getStyleClass().add("chip");
                
                if(activo){
                    chip.setText(FlowController.getIdioma().getString("ein.activo"));
                    chip.getStyleClass().add("chipActivo");
                }else{
                     chip.setText(FlowController.getIdioma().getString("ein.inactivo"));
                    chip.getStyleClass().add("chipInactivo");
                }
                
                setGraphic(chip);
                setText(null);
               
            }

        });

        colEstado.setCellValueFactory(new PropertyValueFactory<>("einActivo"));
        colNotifica.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getEinNotifica()) ? "Sí" : "No"));
        colEstadoFinal.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getEinFinal()) ? "Sí" : "No"));
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));

        this.tblEstadosInstrumento.setPlaceholder(new Label("No hay estados registrados por el momento"));
        this.tblEstadosInstrumento.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        this.tblEstadosInstrumento.setItems(listaEstadosIntrumento);
    }

    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = estadoInstrumentoService.getEstadoInstrumentos(null, null, null, bufeteId);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<EstadoInstrumento> estadosInstrumentoService = (List<EstadoInstrumento>) respuesta.getResultado("EstadosInstrumentos");
            todosLosEstados = estadosInstrumentoService;
            filtrar();

        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "EstadosInstrumentos", "Error cargando los estados de los intrumentos" + respuesta.getMensaje());
        }

    }

    @Override
    public void initialize() {
        btnAgregar.setDisable(!Permisos.tienePermiso("InstrumentosEstadoView", "Crear"));
        cargarTabla();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        AppContext.getInstance().delete("estadoInstrumentoEditar");
        FlowController.getInstance().goViewInWindowModal("RegistrarEstadoInstrumentoView", stage, Boolean.FALSE);
        cargarTabla();
    }

    private void modificarEstadoInstrumento(EstadoInstrumento estadoInstrumento) {
        AppContext.getInstance().set("estadoInstrumentoEditar", estadoInstrumento);
        FlowController.getInstance().goViewInWindowModal("RegistrarEstadoInstrumentoView", stage, false);
        cargarTabla();
    }

    private void filtrar() {
        String descripcion = txtBuscarDescripcion.getText().trim().toLowerCase();

        List<EstadoInstrumento> filtro = todosLosEstados.stream()
                .filter(e -> contiene(e.getEinDescripcion(), descripcion))
                .filter(e -> coincide(e.getEinActivo(), cmbFiltroEstado))
                .filter(e -> coincide(e.getEinNotifica(), cmbFiltroNotifica))
                .collect(Collectors.toList());

        listaEstadosIntrumento.setAll(filtro);
    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }

        return dato != null && dato.toLowerCase().contains(buscado);
    }

    private boolean coincide(Boolean valor, ComboBox cm) {
        int num = cm.getSelectionModel().getSelectedIndex();
        if (num <= 0) {
            return true;
        }

        boolean esSi = Boolean.TRUE.equals(valor);
        return num == 1 ? esSi : !esSi;
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        filtrar();
    }

    @FXML
    private void onActionBtnLimpiarFiltros(ActionEvent event) {
        this.txtBuscarDescripcion.clear();
        this.cmbFiltroEstado.getSelectionModel().selectFirst();
        this.cmbFiltroNotifica.getSelectionModel().selectFirst();
        filtrar();
    }

}
