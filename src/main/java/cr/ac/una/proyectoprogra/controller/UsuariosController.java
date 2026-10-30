/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.UsuarioService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Permisos;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class UsuariosController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnAgregarUsuario;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarApellido;
    @FXML
    private TextField txtBuscarUsuario;
    @FXML
    private TextField txtBuscarIdentificacion;
    @FXML
    private TextField txtBuscarCorreo;
    @FXML
    private TableView<UsuarioDto> tblUsuarios;
    @FXML
    private TableColumn<UsuarioDto, String> colUsuario;
    @FXML
    private TableColumn<UsuarioDto, String> colNombre;
    @FXML
    private TableColumn<UsuarioDto, String> colApellido1;
    @FXML
    private TableColumn<UsuarioDto, String> colApellido2;
    @FXML
    private TableColumn<UsuarioDto, String> colIdentificacion;
    @FXML
    private TableColumn<UsuarioDto, String> colCorreo;
    @FXML
    private TableColumn<UsuarioDto, String> colAdmin;
    @FXML
    private TableColumn<UsuarioDto, String> colEstado;
    @FXML
    private TableColumn<UsuarioDto, UsuarioDto> colAcciones;
    
    private UsuarioService usuarioService = new UsuarioService();
    private List<UsuarioDto> todosLosUsuarios = new ArrayList<>();
    private ObservableList<UsuarioDto> listaUsuarios = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colAcciones.setCellFactory(col -> new TableCell<UsuarioDto, UsuarioDto>() {

            private final Button btnEditar = new Button();
            private final Button btnDesactivar = new Button();
            private final HBox contenedor = new HBox();

            {
                ImageView iconoDesactivar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));
                iconoDesactivar.setFitHeight(30);
                iconoDesactivar.setFitWidth(30);

                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);

                btnDesactivar.setGraphic(iconoDesactivar);
                btnEditar.setGraphic(iconoEditar);
                btnDesactivar.setMinWidth(30);
                btnEditar.setMinWidth(30);
                btnDesactivar.setStyle("-fx-background-color: transparent;");
                btnEditar.setStyle("-fx-background-color: transparent;");
                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(10);

                btnDesactivar.setOnAction(e -> desactivarUsuario(getItem()));
                btnEditar.setOnAction(e -> modificarUsuario(getItem()));
            }

            @Override
            protected void updateItem(UsuarioDto usuario, boolean empty) {
                super.updateItem(usuario, empty);
                if (empty || usuario == null) {
                    setGraphic(null);
                    return;
                }
                contenedor.getChildren().clear();
                if (usuario.getUsrActivo() && Permisos.tienePermiso("UsuariosView", "Eliminar")) {
                    contenedor.getChildren().add(btnDesactivar);
                }
                if (Permisos.tienePermiso("UsuariosView", "Modificar")) {
                    contenedor.getChildren().add(btnEditar);
                }
                setGraphic(contenedor);
            }
        });

        tblUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usrUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("usrNombre"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("usrApellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("usrApellido2"));
        colIdentificacion.setCellValueFactory(new PropertyValueFactory<>("usrIdentificacion"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("usrCorreo"));
        colAdmin.setCellValueFactory(data -> new SimpleStringProperty(textoAdmin(data.getValue())));
        colEstado.setCellValueFactory(data -> new SimpleStringProperty(textoEstado(data.getValue())));
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));

        tblUsuarios.setPlaceholder(new Label("No hay usuarios registrados por el momento"));
        tblUsuarios.setItems(listaUsuarios);
    }    

    @FXML
    private void onActionBtnAgregarUsuario(ActionEvent event) {
        AppContext.getInstance().delete("usuarioEditar");
        FlowController.getInstance().goViewInWindowModal("RegistrarUsuarioView", stage, false);
        cargarTabla();
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @Override
    public void initialize() {
        if (!Permisos.tienePermiso("UsuariosView", "Consultar")) {
            listaUsuarios.clear();
            btnAgregarUsuario.setDisable(true);
            mostrarError("No tiene permiso para ver los usuarios.");
            return;
        }
        btnAgregarUsuario.setDisable(!Permisos.tienePermiso("UsuariosView", "Crear"));
        cargarTabla();
    }
    
    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Respuesta respuesta = usuarioService.getUsuarios(usuarioSesion.getUsrBufeteId());
        if (respuesta.getEstado()) {
            todosLosUsuarios = (List<UsuarioDto>) respuesta.getResultado("Usuarios");
            filtrar();
        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    private void filtrar() {
        String nombre = texto(txtBuscarNombre);
        String apellido = texto(txtBuscarApellido);
        String usuario = texto(txtBuscarUsuario);
        String identificacion = texto(txtBuscarIdentificacion);
        String correo = texto(txtBuscarCorreo);

        List<UsuarioDto> filtrados = new ArrayList<>();
        for (int i = 0; i < todosLosUsuarios.size(); i++) {
            UsuarioDto actual = todosLosUsuarios.get(i);
            if (contiene(actual.getUsrNombre(), nombre) && contiene(actual.getUsrApellido1() + " " + actual.getUsrApellido2(), apellido)
            && contiene(actual.getUsrUsuario(), usuario) && contiene(actual.getUsrIdentificacion(), identificacion) && contiene(actual.getUsrCorreo(), correo)) { 
                filtrados.add(actual);
            }
        }
        listaUsuarios.setAll(filtrados);
    }

    private String texto(TextField campo) {
        if (campo.getText() == null) {
            return "";
        }
        return campo.getText().trim().toLowerCase();
    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }
        return dato != null && dato.toLowerCase().contains(buscado);
    }

    private String textoAdmin(UsuarioDto usuario) {
        if (usuario.getUsrAdmin()) {
            return "Sí";
        } else {
            return "No";
        }
    }

    private String textoEstado(UsuarioDto usuario) {
        if (usuario.getUsrActivo()) {
            return "Activo";
        } else {
            return "Inactivo";
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

    public AnchorPane getRoot() {
        return root;
    }
    
     private void modificarUsuario(UsuarioDto usuario) {
        AppContext.getInstance().set("usuarioEditar", usuario);
        FlowController.getInstance().goViewInWindowModal("RegistrarUsuarioView", stage, false);
        cargarTabla();
    }

    private void desactivarUsuario(UsuarioDto usuario) {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        if (usuario.getUsrId().equals(usuarioSesion.getUsrId())) {
            mostrarError("No puede desactivar su propio usuario.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Desea desactivar al usuario " + usuario.getUsrUsuario() + "?");
        ButtonType btnSi = new ButtonType("Desactivar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();
        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnSi) {
            Respuesta respuesta = usuarioService.desactivarUsuario(usuarioSesion.getUsrBufeteId(), usuario.getUsrId());
            if (respuesta.getEstado()) {
                mostrarExito("El usuario se desactivó correctamente.");
                cargarTabla();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }
    }
}
