/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.service.AbogadoService;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class BusquedaAbogadoController extends Controller implements Initializable {

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
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarCorreo;
    @FXML
    private ComboBox<String> cmbFiltroPropietario;
    @FXML
    private ComboBox<String> cmbFiltroNotario;

    private AbogadoService abogadoService = new AbogadoService();
    private ObservableList<Abogado> listaAbogados = FXCollections.observableArrayList();
    private List<Abogado> todosLosAbogados = new ArrayList<>();

    private final String[] filtroPropietario = {"Todos", "Sí", "No"};
    private final String[] filtroNotario = {"Todos", "Sí", "No"};
    
    private Abogado abogadoSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbFiltroPropietario.getItems().setAll(filtroPropietario);
        cmbFiltroPropietario.getSelectionModel().selectFirst();

        cmbFiltroNotario.getItems().setAll(filtroNotario);
        cmbFiltroNotario.getSelectionModel().selectFirst();

        tblAbogados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // para que las cols se ajusten al contenido

        colNombre.setCellValueFactory(new PropertyValueFactory<>("aboNombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("aboCedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("aboTelefono"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("aboCelular"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("aboCorreo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("aboDireccion"));
        colPropietario.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getAboPropietario()) ? "Sí" : "No"));
        colNotario.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getAboNotario()) ? "Sí" : "No"));

        this.tblAbogados.setPlaceholder(new Label("Busque con los filtros un abogado para cargarlo"));
        tblAbogados.setItems(listaAbogados);
        cargarTabla();
    }

    private void cargarTabla(){
        Long bufeteId = 1L;
        Respuesta respuesta = abogadoService.getAbogados(null, null, null, null, null, bufeteId);
        if(Boolean.TRUE.equals(respuesta.getEstado())){
           List<Abogado> abogadosService = (List<Abogado>) respuesta.getResultado("Abogados");
           todosLosAbogados = abogadosService;
           filtrar();
        }else{
            new Mensaje().show(Alert.AlertType.ERROR, "Abogados", "Error cargando los abogados desde el service" + respuesta.getMensaje());
        }
    }
    
    private void filtrar() {
        String nombre = txtBuscarNombre.getText().trim().toLowerCase();
        String cedula = txtBuscarCedula.getText().trim().toLowerCase();
        String correo = txtBuscarCorreo.getText().trim().toLowerCase();

        List<Abogado> filtrados = todosLosAbogados.stream()
                .filter(a -> contiene(a.getAboNombre(), nombre))
                .filter(a -> contiene(a.getAboCedula(), cedula))
                .filter(a -> contiene(a.getAboCorreo(), correo))
                .filter(a -> coincide(a.getAboPropietario(), cmbFiltroPropietario))
                .filter(a -> coincide(a.getAboNotario(), cmbFiltroNotario))
                .collect(Collectors.toList());

        listaAbogados.setAll(filtrados);
    }
    
        private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }

        return dato != null && dato.toLowerCase().contains(buscado); // devuelve true entonces lo contiene
    }

    private boolean coincide(Boolean valor, ComboBox cm) {
        int num = cm.getSelectionModel().getSelectedIndex();
        if (num <= 0) {
            return true;
        }

        boolean esSi = Boolean.TRUE.equals(valor);
        return num == 1 ? esSi : !esSi;
    }
    
    private void seleccionarAbogado(Long id){
        for(Abogado abogado: listaAbogados){
            if(abogado.getAboId().equals(id)){
                tblAbogados.getSelectionModel().select(abogado);
                // cargar abogado
            }
        }
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

    }

    @FXML
    private void btnOnActionCargarAbogado(ActionEvent event) {
        
        abogadoSeleccionado = tblAbogados.getSelectionModel().getSelectedItem();
        
        if(abogadoSeleccionado == null){
            mostrarError("Seleccione un abogado para cargarlo en la tabla");
            return;
        }
        
        ReportesController reporteController = (ReportesController) FlowController.getInstance().getController("ReportesView");
        reporteController.cargarAbogado(abogadoSeleccionado);
        System.out.println("se seleccio " + abogadoSeleccionado.getAboNombre());
        ((Stage)btnBuscar.getScene().getWindow()).close();
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
