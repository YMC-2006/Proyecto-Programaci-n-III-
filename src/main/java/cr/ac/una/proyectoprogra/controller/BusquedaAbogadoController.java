/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.service.AbogadoService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

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

        this.tblAbogados.setPlaceholder(new Label("No hay abogados registrados por el momento"));
        tblAbogados.setItems(listaAbogados);
        //cargarTabla();
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
    }

    @FXML
    private void btnOnActionBuscarAbogado(ActionEvent event) {
    }

    @Override
    public void initialize() {

    }

}
