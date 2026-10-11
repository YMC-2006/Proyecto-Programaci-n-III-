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
import cr.ac.una.proyectoprogra.model.Abogado;
import cr.ac.una.proyectoprogra.service.AbogadoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
// FALTA AÑDAIR LO DE NOTARIO PQ NO ESTOY SEGURA SI SI VA ENTONCES HAY QUE PREGUNTAR AL PROFE
public class AbogadosController extends Controller implements Initializable {

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
    private TableColumn<Abogado, Abogado> colAcciones;

    private AbogadoService abogadoService = new AbogadoService();

    private ObservableList<Abogado> listaAbogados = FXCollections.observableArrayList();
    private List<Abogado> todosLosAbogados = new ArrayList<>();
    
    @FXML
    private MFXButton btnAgregarAbogado;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtBuscarNombre;
    @FXML
    private TextField txtBuscarCedula;
    @FXML
    private TextField txtBuscarCorreo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colAcciones.setCellFactory(col -> new TableCell<Abogado, Abogado>() {

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
                    Abogado abogado = getItem();
                    eliminarAbogado(abogado);
                });

                btnEditar.setOnAction(e -> {
                    Abogado abogado = getItem();
                    modificarAbogado(abogado);
                });
            }

            @Override
            protected void updateItem(Abogado abogado, boolean empty) {
                super.updateItem(abogado, empty);

                if (empty || abogado == null) {
                    setGraphic(null);
                    return;
                }

                contenedor.getChildren().clear();
                contenedor.getChildren().add(btnEliminar);
                contenedor.getChildren().add(btnEditar);
                setGraphic(contenedor);
            }

        });

        tblAbogados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // para que las cols se ajusten al contenido

        colNombre.setCellValueFactory(new PropertyValueFactory<>("aboNombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("aboCedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("aboTelefono"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("aboCelular"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("aboCorreo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("aboDireccion"));
        colPropietario.setCellValueFactory(data -> new SimpleStringProperty(Boolean.TRUE.equals(data.getValue().getAboPropietario()) ? "Sí" : "No"));

        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));

        this.tblAbogados.setPlaceholder(new Label("No hay abogados registrados por el momento"));

        tblAbogados.setItems(listaAbogados);
        cargarTabla();
    }

    private void cargarTabla() {
        Long bufeteId = 1L;
        Respuesta respuesta = abogadoService.getAbogados(null, null, null, null, null, bufeteId);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Abogado> abogadosService = (List<Abogado>) respuesta.getResultado("Abogados");
            todosLosAbogados = abogadosService; 
            filtrar();
            
            
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Abogados", "Error cargando los abogados desde el service " + respuesta.getMensaje());
        }

    }

    @Override
    public void initialize() {
        cargarTabla();
    }

    @FXML
    private void onActionBtnAgregarAbogado(ActionEvent event) {
        AppContext.getInstance().delete("abogadoEditar"); // cuando vamos a crear queremos que la ventana se abra vacida
        FlowController.getInstance().goViewInWindowModal("RegistrarAbogadoView", stage, false);
        cargarTabla();
    }

    private void modificarAbogado(Abogado abogado) {
        AppContext.getInstance().set("abogadoEditar", abogado);
        FlowController.getInstance().goViewInWindowModal("RegistrarAbogadoView", stage, false);
        cargarTabla();
    }

    private void eliminarAbogado(Abogado abogado) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " + abogado.getAboNombre() + " permanentemente?");

        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);

        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);

        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);

        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();

        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            Respuesta respuesta = abogadoService.eliminarAbogado(abogado.getAboId());

            if (Boolean.TRUE.equals(respuesta.getEstado())) {

                mostrarExito("Se elimino correctamente el abogado");
                cargarTabla();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }

    }
    
    // TODO DONT FORGET AGREGAR UN BOTONCITO PARA LIMPIAR TANTO EN CLIENTE COMO EN ABOGADO
    
    private void filtrar(){
         String nombre = texto(txtBuscarNombre);
         String cedula = texto(txtBuscarCedula);
         String correo = texto(txtBuscarCorreo);
         
         List<Abogado> filtrados = todosLosAbogados.stream()
                 .filter(a -> contiene(a.getAboNombre(), nombre))
                 .filter(a -> contiene(a.getAboCedula(), cedula))
                 .filter(a -> contiene(a.getAboCorreo(), correo))
                 .collect(Collectors.toList());
         
        listaAbogados.setAll(filtrados);
    }
    
    // para obtener el texto del filtro en minuscula
    private String texto(TextField txt){
       return txt.getText() == null ? "" : txt.getText().trim().toLowerCase();
    }

    private boolean contiene(String dato, String buscado){
        if(buscado.isEmpty()){
            return true;
        }
        
        return dato != null && dato.toLowerCase().contains(buscado); // devuelve true entonces lo contiene
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

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }
}
