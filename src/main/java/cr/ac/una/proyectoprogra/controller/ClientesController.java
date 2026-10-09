/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.service.ClienteService;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ClientesController extends Controller implements Initializable {
 
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
    private TableColumn<Cliente, String> colCelular;
    @FXML
    private TableColumn<Cliente, String> colCorreo;
    @FXML
    private TableColumn<Cliente, String> colOcupacion;
    @FXML
    private TableColumn<Cliente, String> colEstadoCivil;
    @FXML
    private TableColumn<Cliente, Cliente> colAcciones;
 
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarApellido;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarCelular;
    @FXML
    private TextField txtBuscarCorreo;
 
    private ClienteService clienteService = new ClienteService();
 
    private List<Cliente> todosLosClientes = new ArrayList<>();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
 
    @FXML
    private MFXButton btnAgregarCliente;
    @FXML
    private AnchorPane root;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colAcciones.setCellFactory(col -> new TableCell<Cliente, Cliente>() {
 
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
                    Cliente cliente = getItem();
                    eliminarCliente(cliente);
                });
 
                btnEditar.setOnAction(e -> {
                    Cliente cliente = getItem();
                    modificarCliente(cliente);
                });
            }
 
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
 
                if (empty || cliente == null) {
                    setGraphic(null);
                    return;
                }
 
                contenedor.getChildren().clear();
                contenedor.getChildren().add(btnEliminar);
                contenedor.getChildren().add(btnEditar);
                setGraphic(contenedor);
            }
 
        });
 
        tblClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
 
        colNombre.setCellValueFactory(new PropertyValueFactory<>("cliNombre"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("cliApellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("cliApellido2"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cliIdentificacion"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("cliCelular"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("cliCorreo"));
        colOcupacion.setCellValueFactory(new PropertyValueFactory<>("cliOcupacion"));
        colEstadoCivil.setCellValueFactory(new PropertyValueFactory<>("cliEstadoCivil"));
 
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
 
        this.tblClientes.setPlaceholder(new Label("No hay clientes registrados por el momento"));
 
        tblClientes.setItems(listaClientes);
        cargarTabla();
    }
 
    private void cargarTabla() {
        Long bufeteId = 1L;
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
        String nombre = texto(txtBuscarNombre);
        String apellido = texto(txtBuscarApellido);
        String cedula = texto(txtBuscarCedula);
        String celular = texto(txtBuscarCelular);
        String correo = texto(txtBuscarCorreo);
 
        List<Cliente> filtrados = todosLosClientes.stream()
                .filter(c -> contiene(c.getCliNombre(), nombre))
                .filter(c -> contiene(c.getCliApellido1() + " " + c.getCliApellido2(), apellido))
                .filter(c -> contiene(c.getCliIdentificacion(), cedula))
                .filter(c -> contiene(c.getCliCelular(), celular))
                .filter(c -> contiene(c.getCliCorreo(), correo))
                .collect(Collectors.toList());
 
        listaClientes.setAll(filtrados);
    }
 
    private String texto(TextField txt) {
        return txt.getText() == null ? "" : txt.getText().trim().toLowerCase();
    }
 
    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }
        return dato != null && dato.toLowerCase().contains(buscado);
    }
 
    @Override
    public void initialize() {
        cargarTabla();
    }
 
    @FXML
    private void onActionBtnAgregarCliente(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("RegistrarClienteView", stage, false);
        cargarTabla();
    }
 
    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }
 
    private void modificarCliente(Cliente cliente) {
        FlowController.getInstance().goViewInWindow("RegistrarClienteView");
        RegistrarClienteController controlador = (RegistrarClienteController) FlowController.getInstance().getController("RegistrarClienteView");
        controlador.cargarCliente(cliente);
        Stage stage = (Stage) controlador.getRoot().getScene().getWindow();
 
        stage.setOnHiding(e -> {
            this.tblClientes.refresh();
        });
    }
 
    private void eliminarCliente(Cliente cliente) {
 
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " + cliente.getCliNombre() + " " + cliente.getCliApellido1() + " permanentemente?");
 
        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);
 
        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);
 
        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);
 
        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();
 
        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            Respuesta respuesta = clienteService.eliminarCliente(cliente.getCliId());
 
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
 
                mostrarExito("Se elimino correctamente el cliente");
                cargarTabla();
            } else {
                mostrarError(respuesta.getMensaje());
            }
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
 
    public AnchorPane getRoot(){
        return root;
    }
}
 