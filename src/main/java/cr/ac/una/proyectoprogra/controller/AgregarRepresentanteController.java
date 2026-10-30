/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Cliente;
import cr.ac.una.proyectoprogra.model.SociedadDto;
import cr.ac.una.proyectoprogra.model.SociedadRepresentanteDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.ClienteService;
import cr.ac.una.proyectoprogra.service.SociedadService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nikole
 */
public class AgregarRepresentanteController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCargo;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<SociedadRepresentanteDto> tblRepresentantes;
    @FXML
    private TableColumn<SociedadRepresentanteDto, String> colCedula;
    @FXML
    private TableColumn<SociedadRepresentanteDto, String> colNombre;
    @FXML
    private TableColumn<SociedadRepresentanteDto, String> colCargo;
    @FXML
    private TableColumn<SociedadRepresentanteDto, SociedadRepresentanteDto> colAcciones;
    
    private List<SociedadRepresentanteDto> todosLosRepresentantes = new ArrayList<>();
    private ObservableList<SociedadRepresentanteDto> listaRepresentantes = FXCollections.observableArrayList();
    private SociedadService sociedadService = new SociedadService();
    private ClienteService clienteService = new ClienteService();
    private Long clienteRepresentanteId;
    
    SociedadDto sociedad;
    private SociedadRepresentanteDto representanteEditando;
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sociedad = (SociedadDto) AppContext.getInstance().get("sociedadRepresentantes");
        colAcciones.setCellFactory(col -> new TableCell<SociedadRepresentanteDto, SociedadRepresentanteDto>() {
 
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
                    SociedadRepresentanteDto representante = getItem();
                    eliminarRepresentantes(representante);
                });
 
                btnEditar.setOnAction(e -> {
                    SociedadRepresentanteDto representante = getItem();
                    modificarRepresentante(representante);
                });
            }
 
            @Override
            protected void updateItem(SociedadRepresentanteDto representante, boolean empty) {
                super.updateItem(representante, empty);
 
                if (empty || representante == null) {
                    setGraphic(null);
                    return;
                }
 
                contenedor.getChildren().clear();
                contenedor.getChildren().add(btnEliminar);
                contenedor.getChildren().add(btnEditar);
                setGraphic(contenedor);
            }
 
        });
 
        tblRepresentantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
 
        colNombre.setCellValueFactory(new PropertyValueFactory<>("sreNombre"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("sreCargo"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("sreCedula"));
 
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
 
        this.tblRepresentantes.setPlaceholder(new Label("No hay representantes agregados por el momento"));
 
        tblRepresentantes.setItems(listaRepresentantes);
        cargarTabla();
    } 
    
    private void cargarTabla() {
        if(sociedad != null && sociedad.getSociedadRepresentanteList() != null){
            todosLosRepresentantes = sociedad.getSociedadRepresentanteList();
            listaRepresentantes.setAll(todosLosRepresentantes);
        }else{
            listaRepresentantes.clear();
        }
    }
    
    private void eliminarRepresentantes(SociedadRepresentanteDto representante) {
 
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " + representante.getSreNombre()+ " permanentemente?");
 
        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);
 
        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);
 
        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);
 
        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();
 
        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            todosLosRepresentantes.remove(representante);
            sociedad.getSociedadRepresentanteEliminadosList().add(representante);
            listaRepresentantes.setAll(todosLosRepresentantes);
            Respuesta respuesta = sociedadService.guardarSociedad(sociedad);
 
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
 
                mostrarExito("Se elimino correctamente el representante");
                cargarTabla();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }
 
    }
    
    private void modificarRepresentante(SociedadRepresentanteDto representante) {
        representanteEditando = representante;
        txtNombre.setText(representante.getSreNombre());
        txtCedula.setText(representante.getSreCedula());
        txtNombre.setEditable(false);
        txtCedula.setEditable(false);
        
        txtCargo.setText(representante.getSreCargo());
        txtCargo.requestFocus();
        this.btnAgregar.setText("Guardar");
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
    
    private void limpiarCampos() {
        representanteEditando = null;
        clienteRepresentanteId = null;
        txtCedula.clear();
        txtNombre.clear();
        txtCargo.clear();
        txtCedula.setEditable(true);
        txtNombre.setEditable(false);
        representanteEditando = null;
        this.btnAgregar.setText("Guardar");
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String cargo = txtCargo.getText();

        if (cedula.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("socRepresentante.error.cedula"));
            txtCedula.requestFocus();
            return;
        }

        if (nombre.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("socRepresentante.error.nombre"));
            txtNombre.requestFocus();
            return;
        }
        
        if (cargo.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("socRepresentante.error.cargo"));
            txtCargo.requestFocus();
            return;
        }

        if (representanteEditando == null) {

            SociedadRepresentanteDto representante = new SociedadRepresentanteDto();
            representante.setSreCedula(cedula);
            representante.setSreNombre(nombre);
            representante.setSreCargo(cargo);
            representante.setSreId(null);
            representante.setSreClienteId(clienteRepresentanteId);
            representante.setSreSociedadId(sociedad.getSocId());
            
            sociedad.getSociedadRepresentanteList().add(representante);
            Respuesta respuesta = sociedadService.guardarSociedad(sociedad);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("socRepresentante.exito.guardar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                sociedad.getSociedadRepresentanteList().remove(representante);
                mostrarError(respuesta.getMensaje());
            }

        } else {
            String cargoAnterior = representanteEditando.getSreCargo();
            representanteEditando.setSreCargo(cargo);
            
            Respuesta respuesta = sociedadService.guardarSociedad(sociedad);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("socRepresentante.exito.modificar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                representanteEditando.setSreCargo(cargoAnterior);
                mostrarError(respuesta.getMensaje());
            }
        }
    }

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBuscarCliente(ActionEvent event) {
        String cedula = txtCedula.getText();
        if(cedula == null || cedula.trim().isEmpty()){
            return;
        }
        
        Respuesta respuesta = clienteService.getClientes(null, null, cedula, null,null, bufeteId);
        if(Boolean.TRUE.equals(respuesta.getEstado())){
            List<Cliente> clientes = (List<Cliente>)respuesta.getResultado("Clientes");
            if(clientes != null && !clientes.isEmpty()){
                Cliente clienteEncontrado = clientes.stream().filter(c->c.getCliIdentificacion() != null && c.getCliIdentificacion().equals(cedula)).findFirst().orElse(null);
                clienteRepresentanteId = clienteEncontrado.getCliId();
                txtNombre.setText(clienteEncontrado.getCliNombre()+" " + clienteEncontrado.getCliApellido1());
            }else{
                mostrarError("No se encontró ningun cliente con ese id");
                txtNombre.clear();
                txtCedula.clear();
                txtCedula.requestFocus();
            }
            
        }else{
            mostrarError("Error al busacar cliente");
        }
    }
    
}
