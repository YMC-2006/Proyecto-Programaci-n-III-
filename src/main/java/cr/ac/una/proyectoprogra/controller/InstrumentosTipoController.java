/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.TipoInstRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class InstrumentosTipoController extends Controller implements Initializable {

    @FXML
    private TextField txtBuscarInstrumento;
    @FXML
    private TableView<TipoInstrumento> tblTipoInstrumentos;
    @FXML
    private TableColumn<TipoInstrumento, String> colNombre;
    @FXML
    private TableColumn<TipoInstrumento, Boolean> colEstado;
    @FXML
    private TextField txtRequisito;
    @FXML
    private Button btnRequisito;
    @FXML
    private ComboBox<String> cmbFiltroEstado;
    private final String[] filtroEstado = {"Todos", "Activo", "Inactivo"};

    private ObservableList<TipoInstrumento> listaTipoInstrumentos = FXCollections.observableArrayList();
    private TipoInstrumentoService tipoInstrumentoService = new TipoInstrumentoService();
    private List<TipoInstrumento> todosLosTiposInstrumentos = new ArrayList<>();

    @FXML
    private TableColumn<TipoInstrumento, TipoInstrumento> colAcciones;
    // agregar el service del requisito del tipo de instrumento 
    @FXML
    private MFXButton btnSubirWord;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cmbFiltroEstado.getItems().setAll(filtroEstado);
        cmbFiltroEstado.getSelectionModel().selectFirst();

        txtRequisito.setTextFormatter(Formato.getInstance().letrasFormat(200)); // en la BD tenemos 300 pero 200 es mas que suficiente  
        txtBuscarInstrumento.setTextFormatter(Formato.getInstance().letrasFormat(100));

        colAcciones.setCellFactory(col -> new TableCell<TipoInstrumento, TipoInstrumento>() {

            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();
            private final HBox contenedor = new HBox();

            {
                ImageView iconoEditar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/editar.png")));
                ImageView iconoEliminar = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/eliminar.png")));

                iconoEditar.setFitHeight(30);
                iconoEditar.setFitWidth(30);

                iconoEliminar.setFitWidth(30);
                iconoEliminar.setFitHeight(30);

                btnEditar.setGraphic(iconoEditar);
                btnEditar.setMinWidth(30);
                btnEditar.setStyle("-fx-background-color: transparent;");

                btnEliminar.setGraphic(iconoEliminar);
                btnEliminar.setMinWidth(30);
                btnEliminar.setStyle("-fx-background-color: transparent;");

                contenedor.setStyle("-fx-alignment: CENTER;");
                contenedor.setSpacing(5);

                btnEditar.setOnAction(e -> {
                    TipoInstrumento tipoInstrumento = getItem();
                    modificarTipoInstrumento(tipoInstrumento);
                });

                btnEliminar.setOnAction(e -> {
                    TipoInstrumento tipoInstrumento = getItem();
                    eliminarTipoInstrumento(tipoInstrumento);

                });

            }

            @Override
            protected void updateItem(TipoInstrumento tipoInstrumento, boolean vacido) {
                super.updateItem(tipoInstrumento, vacido);

                if (vacido || tipoInstrumento == null) {
                    setGraphic(null);
                    return;
                }

                contenedor.getChildren().clear();
                contenedor.getChildren().addAll(btnEditar, btnEliminar);
                setGraphic(contenedor);
            }

        });

        colEstado.setCellFactory(col -> new TableCell<TipoInstrumento, Boolean>() {

            @Override
            protected void updateItem(Boolean activo, boolean vacido) {
                super.updateItem(activo, vacido);

                if (vacido || activo == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                Label chip = new Label();
                chip.getStyleClass().add("chip");

                if (activo) {
                    chip.setText(FlowController.getIdioma().getString("ein.activo"));
                    chip.getStyleClass().add("chipActivo");
                } else {
                    chip.setText(FlowController.getIdioma().getString("ein.inactivo"));
                    chip.getStyleClass().add("chipInactivo");
                }

                setGraphic(chip);
                setText(null);

            }

        }
        );

        colNombre.setCellValueFactory(new PropertyValueFactory<>("tinNombre"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("tinEstado"));
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
        this.tblTipoInstrumentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        this.tblTipoInstrumentos.setPlaceholder(new Label("No hay tipo de instrumentos por el momento"));
        this.tblTipoInstrumentos.setItems(listaTipoInstrumentos);
        cargarTabla();
    }

    @Override
    public void initialize() {
        cargarTabla();
    }

    @FXML
    private void onActionBtnAgregarRequisito(ActionEvent event) {

        String descRequisito = this.txtRequisito.getText();

        if (descRequisito.isBlank() || descRequisito.isEmpty()) {
            mostrarError("El requisito no puede estar vacido");
            return;
        }

        TipoInstRequisito requisito = new TipoInstRequisito(descRequisito);

    }

    private void cargarTabla() {

        Long bufeteId = 1L;
        Respuesta respuesta = tipoInstrumentoService.getTipoInstrumentos(null, null, bufeteId);

        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<TipoInstrumento> tipoInstrumentosService = (List<TipoInstrumento>) respuesta.getResultado("TipoInstrumentos");
            todosLosTiposInstrumentos = tipoInstrumentosService;
            filtrar();

        } else {
            new Mensaje().show(Alert.AlertType.ERROR, "TipoInstrumentos", "Error cargando los tipos de intrumentos" + respuesta.getMensaje());
        }
    }

    private void filtrar() {
        String tinNombre = txtBuscarInstrumento.getText().trim().toLowerCase();

        Function<String, Predicate<TipoInstrumento>> contieneTexto = x -> t -> t.getTinNombre().equals(x);

        List<TipoInstrumento> listaFiltrada = todosLosTiposInstrumentos.stream()
                .filter(t -> contiene(t.getTinNombre(), tinNombre))
                .filter(t -> coincide(t.getTinEstado(), cmbFiltroEstado))
                .collect(Collectors.toList());

        listaTipoInstrumentos.setAll(listaFiltrada);

    }

    private boolean contiene(String dato, String buscado) {
        if (buscado.isEmpty()) {
            return true;
        }

        return dato != null && dato.toLowerCase().contains(buscado);
    }

    private boolean coincide(Boolean valor, ComboBox cm) {
        int num = cm.getSelectionModel().getSelectedIndex();
        if (num <= 0) {
            return true;
        }

        boolean esSi = Boolean.TRUE.equals(valor);
        return num == 1 ? esSi : !esSi;
    }

    private void modificarTipoInstrumento(TipoInstrumento tipoInstrumento) {
        AppContext.getInstance().set("tipoInstrumentoEditar", tipoInstrumento);
        FlowController.getInstance().goViewInWindowModal("RegistrarTipoInstrumentoView", stage, false);
        cargarTabla();

    }

    private void eliminarTipoInstrumento(TipoInstrumento tipoInstrumento) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("\n Desea eliminar a " + tipoInstrumento.getTinNombre() + " permanentemente?");

        ButtonType btnEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(btnEliminar, btnCancelar);

        Button botonCancelar = (Button) alerta.getDialogPane().lookupButton(btnCancelar);
        Button botonEliminar = (Button) alerta.getDialogPane().lookupButton(btnEliminar);

        botonCancelar.setDefaultButton(true);
        botonEliminar.setDefaultButton(false);

        Optional<ButtonType> respuestaDialogo = alerta.showAndWait();

        if (respuestaDialogo.isPresent() && respuestaDialogo.get() == btnEliminar) {
            Respuesta respuesta = tipoInstrumentoService.eliminarTipoInstrumento(tipoInstrumento.getTinId());

            if (Boolean.TRUE.equals(respuesta.getEstado())) {

                mostrarExito("Tipo de instrumento eliminado con éxito");
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

    @FXML
    private void onActionBtnRegistrarTipoInstrumento(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("RegistrarTipoInstrumentoView", stage, false);
        cargarTabla();

    }

    @FXML
    private void onActionFiltrar(ActionEvent event) {
        filtrar();
    }

    @FXML
    private void onKeyReleasedFiltrar(KeyEvent event) {
        filtrar();
    }

    @FXML
    private void onActionBtnSubirMachoteWord(ActionEvent event) {

        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Documentos de Word (*.docx)", "*.docx");
        FileChooser exploradorArchivos = new FileChooser();
        exploradorArchivos.setTitle("Explorador de Archivos");
        exploradorArchivos.getExtensionFilters().addAll(imgFilter);
        File archivoSeleccionado = exploradorArchivos.showOpenDialog(btnSubirWord.getScene().getWindow());

        if (archivoSeleccionado != null) {
            if (!archivoSeleccionado.getName().toLowerCase().endsWith(".docx")) {
                mostrarError("El machote a subir tiene que ser un word .docx");
                return;
            }
            try {
                byte[] contenidoWord = Files.readAllBytes(archivoSeleccionado.toPath());
                // este es lo que tenemos que mandar al ws
                // preguntar al profe si asi eestara bien TO-DO
            } catch (IOException e) {
                System.out.println("Error no se pudo leer el archivo: " + e.getMessage());
            }

        }
    }
}
