package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Parametro;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.ParametroService;
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
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ParametrosController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregar;
    @FXML
    private TextField txtBuscar;
    @FXML
    private MFXButton btnLimpiar;
    @FXML
    private TableView<Parametro> tblParametros;
    @FXML
    private TableColumn<Parametro, String> colCodigo;
    @FXML
    private TableColumn<Parametro, String> colValor;
    @FXML
    private TableColumn<Parametro, String> colDescripcion;
    @FXML
    private TableColumn<Parametro, Parametro> colAcciones;

    // parametros que usa el sistema (el WS los busca por codigo), se pueden editar pero no eliminar
    private final List<String> codigosSistema = List.of("RUTA_DOCUMENTOS");

    private ObservableList<Parametro> listaParametros = FXCollections.observableArrayList();
    private List<Parametro> todosLosParametros = new ArrayList<>();
    private ParametroService parametroService = new ParametroService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colAcciones.setCellFactory(col -> new TableCell<Parametro, Parametro>() {

            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();
            private final ImageView iconoCandado = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/contrasena.png")));
            private final HBox contenedor = new HBox();

            {
                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));

                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);
                iconoEliminar.setFitHeight(30);
                iconoEliminar.setFitWidth(30);
                iconoCandado.setFitHeight(22);
                iconoCandado.setFitWidth(22);

                btnEditar.setGraphic(iconoEditar);
                btnEditar.setMinWidth(30);
                btnEditar.setStyle("-fx-background-color: transparent;");

                btnEliminar.setGraphic(iconoEliminar);
                btnEliminar.setMinWidth(30);
                btnEliminar.setStyle("-fx-background-color: transparent;");

                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(5);

                btnEditar.setOnAction(e -> modificarParametro(getItem()));
                btnEliminar.setOnAction(e -> eliminarParametro(getItem()));
            }

            @Override
            protected void updateItem(Parametro parametro, boolean vacido) {
                super.updateItem(parametro, vacido);

                if (vacido || parametro == null) {
                    setGraphic(null);
                    return;
                }

                contenedor.getChildren().clear();
                if (Permisos.tienePermiso("ParametrosView", "Modificar")) {
                    contenedor.getChildren().add(btnEditar);
                }
                if (esDelSistema(parametro)) {
                    contenedor.getChildren().add(iconoCandado);
                } else if (Permisos.tienePermiso("ParametrosView", "Eliminar")) {
                    contenedor.getChildren().add(btnEliminar);
                }
                setGraphic(contenedor);
            }
        });

        // el codigo se ve como "chip" gris para que se lea como nombre tecnico
        colCodigo.setCellFactory(col -> new TableCell<Parametro, String>() {
            @Override
            protected void updateItem(String codigo, boolean vacido) {
                super.updateItem(codigo, vacido);

                if (vacido || codigo == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                Label chip = new Label(codigo);
                chip.getStyleClass().add("chipCodigo");
                setGraphic(chip);
                setText(null);
            }
        });

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("parCodigo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("parValor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("parDescripcion"));
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));

        this.tblParametros.setPlaceholder(new Label(FlowController.getIdioma().getString("par.placeholder")));
        this.tblParametros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        this.tblParametros.setItems(listaParametros);
    }

    @Override
    public void initialize() {
        btnAgregar.setDisable(!Permisos.tienePermiso("ParametrosView", "Crear"));
        cargarTabla();
    }

    private void cargarTabla() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        Long bufeteId = usuarioSesion.getUsrBufeteId();
        Respuesta respuesta = parametroService.getParametros(bufeteId);

        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            todosLosParametros = (List<Parametro>) respuesta.getResultado("Parametros");
            filtrar();
        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "Parametros", "Error cargando los parámetros " + respuesta.getMensaje());
        }
    }

    private void filtrar() {
        String buscado = txtBuscar.getText().trim().toLowerCase();

        // un solo buscador: coincide si el texto esta en el codigo, el valor o la descripcion
        List<Parametro> filtro = todosLosParametros.stream()
                .filter(p -> contiene(p.getParCodigo(), buscado)
                || contiene(p.getParValor(), buscado)
                || contiene(p.getParDescripcion(), buscado))
                .collect(Collectors.toList());

        listaParametros.setAll(filtro);
    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }
        return dato != null && dato.toLowerCase().contains(buscado);
    }

    private boolean esDelSistema(Parametro parametro) {
        return parametro.getParCodigo() != null && codigosSistema.contains(parametro.getParCodigo());
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        AppContext.getInstance().delete("parametroEditar");
        FlowController.getInstance().goViewInWindowModal("RegistrarParametroView", stage, false);
        cargarTabla();
    }

    private void modificarParametro(Parametro parametro) {
        AppContext.getInstance().set("parametroEditar", parametro);
        FlowController.getInstance().goViewInWindowModal("RegistrarParametroView", stage, false);
        cargarTabla();
    }

    private void eliminarParametro(Parametro parametro) {
        if (esDelSistema(parametro)) {
            mostrarError(FlowController.getIdioma().getString("par.error.sistema"));
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n¿Desea eliminar el parámetro " + parametro.getParCodigo() + " permanentemente?");

        ButtonType btnSi = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNo = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnSi, btnNo);
        ((Button) alerta.getDialogPane().lookupButton(btnNo)).setDefaultButton(true);
        ((Button) alerta.getDialogPane().lookupButton(btnSi)).setDefaultButton(false);

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == btnSi) {
            Respuesta respuesta = parametroService.eliminarParametro(parametro.getParId());

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
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

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @FXML
    private void onActionBtnLimpiarFiltros(ActionEvent event) {
        this.txtBuscar.clear();
        filtrar();
    }
}