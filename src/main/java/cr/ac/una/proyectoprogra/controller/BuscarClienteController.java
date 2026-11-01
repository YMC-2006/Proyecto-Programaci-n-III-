/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.ClienteService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class BuscarClienteController extends Controller implements Initializable {

    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarApellido1;
    @FXML
    private TextField txtBuscarApellido2;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarOcupacion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellido1;
    @FXML
    private TableColumn<Cliente, String> colApellido2;
    @FXML
    private TableColumn<Cliente, String> colCedula;
    @FXML
    private TableColumn<Cliente, String> colOcupacion;

    private ClienteService clienteService = new ClienteService();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private List<Cliente> todosLosClientes = new ArrayList<>();

    private Cliente clienteSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("cliNombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cliCedula"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("cliApellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("cliApellido2"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cliIdentificacion"));
        colOcupacion.setCellValueFactory(new PropertyValueFactory<>("cliOcupacion"));

        this.tblClientes.setPlaceholder(new Label("Busque con los filtros un cliente para cargarlo"));
        tblClientes.setItems(listaClientes);

        // para que se cargue con doble click
        tblClientes.setRowFactory(tabla -> {
            TableRow<Cliente> fila = new TableRow<>();
            fila.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !fila.isEmpty()) {
                    tblClientes.getSelectionModel().select(fila.getItem());
                    btnOnActionCargarCliente(null);
                }
            });
            return fila;
        });

        cargarTabla();
    }

    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = clienteService.getClientes(null, null, null, null, null, bufeteId);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Cliente> clientesService = (List<Cliente>) respuesta.getResultado("Clientes");
            todosLosClientes = clientesService;
            filtrar();
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Clientes", "Error cargando los clientes desde el service " + respuesta.getMensaje());
        }
    }

    private void filtrar() {
        String nombre = txtBuscarNombre.getText().trim().toLowerCase();
        String cedula = txtBuscarCedula.getText().trim().toLowerCase();
        String apellido1 = txtBuscarApellido1.getText().trim().toLowerCase();
        String apellido2 = txtBuscarApellido2.getText().trim().toLowerCase();
        String ocupacion = txtBuscarOcupacion.getText().trim().toLowerCase();

        List<Cliente> filtrados = todosLosClientes.stream()
                .filter(a -> contiene(a.getCliNombre(), nombre))
                .filter(a -> contiene(a.getCliIdentificacion(), cedula))
                .filter(a -> contiene(a.getCliApellido1(), apellido1))
                .filter(a -> contiene(a.getCliApellido2(), apellido2))
                .filter(a -> contiene(a.getCliOcupacion(), ocupacion))
                .collect(Collectors.toList());

        listaClientes.setAll(filtrados);
    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }

        return dato != null && dato.toLowerCase().contains(buscado); // devuelve true entonces lo contiene
    }

    @Override
    public void initialize() {
        clienteSeleccionado = null;
        txtBuscarNombre.clear();
        txtBuscarApellido1.clear();
        txtBuscarApellido2.clear();
        txtBuscarCedula.clear();
        txtBuscarOcupacion.clear();
        tblClientes.getSelectionModel().clearSelection();
        cargarTabla();
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @FXML
    private void btnOnActionCargarCliente(ActionEvent event) {
        clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            mostrarError("Seleccione un cliente para cargarlo");
            return;
        }

        System.out.println("se selecciono " + clienteSeleccionado.getCliNombre());
        AppContext.getInstance().set("clienteSeleccionado", clienteSeleccionado);
        ((Stage) btnBuscar.getScene().getWindow()).close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
