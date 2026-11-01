/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.SociedadDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.SociedadService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nikole
 */
public class BusquedaSociedadController extends Controller implements Initializable {

    @FXML
    private TableView<SociedadDto> tblSociedades;
    @FXML
    private TableColumn<SociedadDto, String> colCedula;
    @FXML
    private TableColumn<SociedadDto, String> colNombre;
    @FXML
    private TableColumn<SociedadDto, String> colTelefono;
    @FXML
    private TableColumn<SociedadDto, String> colCorreo;
    @FXML
    private TableColumn<SociedadDto, String> colDomicilio;
    @FXML
    private TableColumn<SociedadDto, String> colFechaInscripcion;
    @FXML
    private TableColumn<SociedadDto, String> colTipoAcciones;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarTelefono;
    @FXML
    private ComboBox<String> cmbFiltroTipoAcciones;

    private SociedadService sociedadService = new SociedadService();
    private ObservableList<SociedadDto> listaSociedades = FXCollections.observableArrayList();
    private List<SociedadDto> todasLasSociedades = new ArrayList<>();

    private final String[] filtroTipoAcciones = {"Todas", "Comunes", "Preferentes"};
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private SociedadDto sociedadSeleccionada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbFiltroTipoAcciones.getItems().setAll(filtroTipoAcciones);
        cmbFiltroTipoAcciones.getSelectionModel().selectFirst();

        tblSociedades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colCedula.setCellValueFactory(new PropertyValueFactory<>("socCedulaJuridica"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("socNombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("socTelefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("socCorreo"));
        colDomicilio.setCellValueFactory(new PropertyValueFactory<>("socDomicilio"));
        colFechaInscripcion.setCellValueFactory(data -> new SimpleStringProperty(
        data.getValue().getSocFechaInscripcion() == null ? "" : data.getValue().getSocFechaInscripcion().format(formatoFecha)));
        colTipoAcciones.setCellValueFactory(data -> new SimpleStringProperty("P".equals(data.getValue().getSocTipoAcciones()) ? "Preferentes" : "Comunes"));

        this.tblSociedades.setPlaceholder(new Label("Busque con los filtros una sociedad para cargarla"));
        tblSociedades.setItems(listaSociedades);
        cargarTabla();

        // para que se cargue con doble click
        tblSociedades.setRowFactory(tabla -> {
            TableRow<SociedadDto> fila = new TableRow<>();
            fila.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !fila.isEmpty()) {
                    tblSociedades.getSelectionModel().select(fila.getItem());
                    btnOnActionCargarSociedad(null);
                }
            });
            return fila;
        });
    }

    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = sociedadService.getSociedades(bufeteId, null, null, null, null);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<SociedadDto> sociedadesService = (List<SociedadDto>) respuesta.getResultado("Sociedades");
            todasLasSociedades = sociedadesService;
            filtrar();
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Sociedades", "Error cargando las sociedades desde el service " + respuesta.getMensaje());
        }
    }

    private void filtrar() {
        String nombre = txtBuscarNombre.getText().trim().toLowerCase();
        String cedula = txtBuscarCedula.getText().trim().toLowerCase();
        String telefono = txtBuscarTelefono.getText().trim().toLowerCase();

        List<SociedadDto> filtradas = todasLasSociedades.stream()
                .filter(s -> contiene(s.getSocNombre(), nombre))
                .filter(s -> contiene(s.getSocCedulaJuridica(), cedula))
                .filter(s -> contiene(s.getSocTelefono(), telefono))
                .filter(s -> coincideTipoAcciones(s.getSocTipoAcciones()))
                .collect(Collectors.toList());

        listaSociedades.setAll(filtradas);
    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }

        return dato != null && dato.toLowerCase().contains(buscado); // devuelve true entonces lo contiene
    }

    private boolean coincideTipoAcciones(String tipo) {
        int num = cmbFiltroTipoAcciones.getSelectionModel().getSelectedIndex();
        if (num <= 0) {
            return true;
        }
        return num == 1 ? "C".equals(tipo) : "P".equals(tipo);
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        filtrar();
    }

    @Override
    public void initialize() {
        sociedadSeleccionada = null;
        txtBuscarNombre.clear();
        txtBuscarCedula.clear();
        txtBuscarTelefono.clear();
        cmbFiltroTipoAcciones.getSelectionModel().selectFirst();
        tblSociedades.getSelectionModel().clearSelection();
        cargarTabla();
    }

    @FXML
    private void btnOnActionCargarSociedad(ActionEvent event) {
        sociedadSeleccionada = tblSociedades.getSelectionModel().getSelectedItem();

        if (sociedadSeleccionada == null) {
            mostrarError("Seleccione una sociedad para cargarla");
            return;
        }

        System.out.println("se selecciono " + sociedadSeleccionada.getSocNombre());
        AppContext.getInstance().set("sociedadSeleccionada", sociedadSeleccionada);
        ((Stage) btnBuscar.getScene().getWindow()).close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}