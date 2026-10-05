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
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
// FALTA AÑDAIR LO DE NOTARIO PQ NO ESTOY SEGURA SI SI VA ENTONCES HAY QUE PREGUNTAR AL PROFE
public class AbogadosController extends Controller implements Initializable {

    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnAgregar;
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

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txaDireccion;
    @FXML
    private CheckBox chkPropietario;
    @FXML
    private CheckBox chkNotario;
    @FXML
    private MFXButton btnLimpiar;

    private AbogadoService abogadoService = new AbogadoService();

    private ObservableList<Abogado> listaAbogados = FXCollections.observableArrayList();

    private Abogado abogadoEditando;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // revisar
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(150));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(30));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtTelefono.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txtCelular.setTextFormatter(Formato.getInstance().maxLengthFormat(20));
        txaDireccion.setTextFormatter(Formato.getInstance().maxLengthFormat(300));

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
                    cargarAbogado(abogado); // cargamos los datos para editarlo en el form
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

    }

    private void cargarTabla() {
        Long bufeteId = 1L;
        Respuesta respuesta = abogadoService.getAbogados(null, null, null, null, null, bufeteId);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Abogado> abogadosService = (List<Abogado>) respuesta.getResultado("Abogados");
            listaAbogados.setAll(abogadosService);
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Abogados", "Error cargando los abogados desde el service " + respuesta.getMensaje());
        }

    }

    @Override
    public void initialize() {
        cargarTabla();
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("BusquedaAbogadoView");
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {

        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String celular = txtCelular.getText();
        String direccion = txaDireccion.getText();
        boolean propietario = chkPropietario.isSelected();

        if (nombre.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.nombre"));
            txtNombre.requestFocus();
            return;
        }

        if (cedula.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.cedula"));
            txtCedula.requestFocus();
            return;
        }

        if (correo.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.correo"));
            txtCorreo.requestFocus();
            return;
        }

        if (telefono.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.telefono"));
            txtTelefono.requestFocus();
            return;
        }

        if (celular.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.celular"));
            txtCelular.requestFocus();
            return;
        }

        if (direccion.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("abogado.error.direccion"));
            txaDireccion.requestFocus();
            return;
        }

        // estoy creando
        if (abogadoEditando == null) {

            // mando notario false por el momento falta preguntarle al profe
            Abogado abogado = new Abogado(nombre, cedula, telefono, celular, correo, direccion, propietario, chkNotario.isSelected());
            abogado.setAboBufeteId(1L);
            Respuesta respuesta = abogadoService.guardarAbogado(abogado);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("abogado.exito.guardar"));
                onActionBtnLimpiarCampos(null);

                cargarTabla();

            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else { // estoy editando
            
            
            
            abogadoEditando.setAboNombre(nombre);
            abogadoEditando.setAboCedula(cedula);
            abogadoEditando.setAboCorreo(correo);
            abogadoEditando.setAboTelefono(telefono);
            abogadoEditando.setAboCelular(celular);
            abogadoEditando.setAboDireccion(direccion);
            abogadoEditando.setAboNotario(chkNotario.isSelected());
            abogadoEditando.setAboPropietario(chkPropietario.isSelected());
            abogadoEditando.setAboBufeteId(1L);
            Respuesta respuesta = abogadoService.guardarAbogado(abogadoEditando);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("abogado.exito.modificar"));
                onActionBtnLimpiarCampos(null);
                cargarTabla();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        }

    }
    

    // se puede mejorar preguntar al profe
    private void cargarAbogado(Abogado abogado) {
        abogadoEditando = abogado;

        txtNombre.setText(abogado.getAboNombre());
        txtCedula.setText(abogado.getAboCedula());
        txtCorreo.setText(abogado.getAboCorreo());
        txtTelefono.setText(abogado.getAboTelefono());
        txtCelular.setText(abogado.getAboCelular());
        txaDireccion.setText(abogado.getAboDireccion());
        chkNotario.setSelected(abogado.getAboNotario());
        chkPropietario.setSelected(abogado.getAboPropietario());

        this.btnAgregar.setText("Editar");
    }

    private void eliminarAbogado(Abogado abogado) {

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " + abogado.getAboNombre()+ " permanentemente?");

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
                
                // validacion pinche hermosa mostrar al profe
                if(abogadoEditando != null && abogadoEditando.getAboId().equals(abogado.getAboId())){
                    onActionBtnLimpiarCampos(null);
                }
                mostrarExito("Se elimino correctamente el abogado");
                cargarTabla();
            } else {
              mostrarError(respuesta.getMensaje());
            }
        }

    }

    @FXML
    private void onActionBtnLimpiarCampos(ActionEvent event) {
        txtNombre.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCelular.clear();
        txaDireccion.clear();
        if (chkNotario.isSelected()) {
            chkNotario.setSelected(false);
        }
        if (chkPropietario.isSelected()) {
            chkPropietario.setSelected(false);
        }
        abogadoEditando = null;
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
}
