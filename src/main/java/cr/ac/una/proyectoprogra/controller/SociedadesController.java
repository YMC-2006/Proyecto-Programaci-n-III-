/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.SociedadDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.SociedadService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Permisos;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class SociedadesController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregarSociedad;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarDomicilio;
    @FXML
    private ComboBox<String> cbxTipoAcciones;
    @FXML
    private TableColumn<SociedadDto, String> colNombre;
    @FXML
    private TableColumn<SociedadDto, String> colCedula;
    @FXML
    private TableColumn<SociedadDto, String> colTelefono;
    @FXML
    private TableColumn<SociedadDto, String> colAcciones;
    @FXML
    private TableColumn<SociedadDto, String> colCorreo;
    @FXML
    private TableColumn<SociedadDto, String> colDomicilio;
    @FXML
    private TableColumn<SociedadDto, SociedadDto> colOpciones;
    
    private SociedadService sociedadService = new SociedadService();
 
    private List<SociedadDto> todosLasSociedades = new ArrayList<>();
    private ObservableList<SociedadDto> listaSociedades = FXCollections.observableArrayList();
    @FXML
    private TableView<SociedadDto> tblSociedades;

    private final String[] filtroAcciones = {"Todas", "Comunes", "Preferentes"};
    @FXML
    private AnchorPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxTipoAcciones.getItems().setAll(filtroAcciones);
        cbxTipoAcciones.getSelectionModel().selectFirst();
        cbxTipoAcciones.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue)->{
            filtrar();
        });
        colOpciones.setCellFactory(col -> new TableCell<SociedadDto, SociedadDto>() {
 
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();
            private final Button btnRepresentante = new Button();
            private final HBox contenedor = new HBox();
 
            {
 
                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));
                iconoEliminar.setFitHeight(30);
                iconoEliminar.setFitWidth(30);
 
                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);
                
                ImageView iconoRepresentante = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/clientes.png")));
                iconoRepresentante.setFitHeight(30);
                iconoRepresentante.setFitWidth(30);
 
                btnEliminar.setGraphic(iconoEliminar);
                btnEditar.setGraphic(iconoEditar);
                btnRepresentante.setGraphic(iconoRepresentante);
 
                btnEliminar.setMinWidth(30);
                btnEditar.setMinWidth(30);
                btnRepresentante.setMinWidth(30);
 
                btnEliminar.setStyle("-fx-background-color: transparent;");
                btnEditar.setStyle("-fx-background-color: transparent;");
                btnRepresentante.setStyle("-fx-background-color: transparent;");
 
                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(10);
 
                btnEliminar.setOnAction(e -> {
                    SociedadDto sociedadDto = getItem();
                    eliminarSociedad(sociedadDto);
                });
 
                btnEditar.setOnAction(e -> {
                    SociedadDto sociedadDto = getItem();
                    modificarSociedad(sociedadDto);
                });
                
                btnRepresentante.setOnAction(e -> {
                    SociedadDto sociedadDto = getItem();
                    representantesSociedad(sociedadDto);
                });
            }
 
            @Override
            protected void updateItem(SociedadDto sociedadDto, boolean empty) {
                super.updateItem(sociedadDto, empty);
 
                if (empty || sociedadDto == null) {
                    setGraphic(null);
                    return;
                }
 
                contenedor.getChildren().clear();
                if (Permisos.tienePermiso("SociedadesView", "Eliminar")) {
                    contenedor.getChildren().add(btnEliminar);
                }
                if (Permisos.tienePermiso("SociedadesView", "Modificar")) {
                    contenedor.getChildren().add(btnEditar);
                    contenedor.getChildren().add(btnRepresentante);
                }
                setGraphic(contenedor);
            }
 
        });
 
        tblSociedades.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
 
        colNombre.setCellValueFactory(new PropertyValueFactory<>("socNombre"));
        colDomicilio.setCellValueFactory(new PropertyValueFactory<>("socDomicilio"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("socTelefono"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("socCedulaJuridica"));
        colAcciones.setCellValueFactory(cellData->{
            SimpleStringProperty acciones = new SimpleStringProperty();
            if(cellData.getValue().getSocTipoAcciones().equals("C")){
                acciones.set("Comunes");
            }else{
                acciones.set("Preferentes");
            }
            return acciones;
        });
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("socCorreo"));
 
        colOpciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
 
        this.tblSociedades.setPlaceholder(new Label("No hay clientes registrados por el momento"));
 
        tblSociedades.setItems(listaSociedades);
        cargarTabla();
    }   
    
    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = sociedadService.getSociedades(bufeteId,null, null, null, null);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<SociedadDto> sociedadesService = (List<SociedadDto>) respuesta.getResultado("Sociedades");
            todosLasSociedades = sociedadesService;
            filtrar();
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Sociedades", "Error cargando las sociedades desde el service " + respuesta.getMensaje());
        }
 
    }
 
    private void filtrar() {
        String nombre = texto(txtBuscarNombre);
        String domicilio = texto(txtBuscarDomicilio);
        String cedula = texto(txtBuscarCedula);
        String tAcciones = filtroTipoAcciones();
 
        List<SociedadDto> filtradas = todosLasSociedades.stream()
                .filter(c -> contiene(c.getSocNombre(), nombre))
                .filter(c -> contiene(c.getSocDomicilio(), domicilio))
                .filter(c -> contiene(c.getSocCedulaJuridica(), cedula))
                .filter(c -> tAcciones == null || c.getSocTipoAcciones().equals(tAcciones))
                .collect(Collectors.toList());
 
        listaSociedades.setAll(filtradas);
    }
    
    private String filtroTipoAcciones(){
        int index = cbxTipoAcciones.getSelectionModel().getSelectedIndex();
        return index == 1 ? "C" : (index == 2 ? "P" : null);
    }
 
    // para obtener el texto en minuscula a la hora de hacer los filtros
    private String texto(TextField txt) {
        return txt.getText() == null ? "" : txt.getText().trim().toLowerCase();
    }
    
    private boolean contiene(String dato, String buscado) {
        if (buscado == null || buscado.isEmpty()) {
            return true;
        }
        return dato != null && dato.toLowerCase().contains(buscado);
    }
    
    private void modificarSociedad(SociedadDto sociedad) {
        AppContext.getInstance().set("sociedadEditar", sociedad);
        FlowController.getInstance().goViewInWindowModal("RegistrarSociedadView", stage, false);
        cargarTabla();
    }
    
    private void representantesSociedad(SociedadDto sociedad) {
        AppContext.getInstance().set("sociedadRepresentantes", sociedad);
        FlowController.getInstance().goViewInWindowModal("AgregarRepresentanteView", stage, false);
        cargarTabla();
    }
 
    private void eliminarSociedad(SociedadDto sociedad) {
 
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " +sociedad.getSocNombre() + " permanentemente?");
 
        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);
 
        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);
 
        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);
 
        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();
 
        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            Respuesta respuesta = sociedadService.eliminarSociedad(sociedad.getSocId());
 
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
 
                mostrarExito("Se elimino correctamente la sociedad");
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

    @Override
    public void initialize() {
        btnAgregarSociedad.setDisable(!Permisos.tienePermiso("SociedadesView", "Crear"));
        cargarTabla();
    }

    @FXML
    private void onActionBtnAgregarSociedad(ActionEvent event) {
        AppContext.getInstance().delete("sociedadEditar");
        FlowController.getInstance().goViewInWindowModal("RegistrarSociedadView", stage, false);
        cargarTabla();
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }
    
    public AnchorPane getRoot(){
        return root;
    }
}
