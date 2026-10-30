/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Comodin;
import cr.ac.una.proyectoprogra.model.TipoInstRequisito;
import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Mensaje;
import cr.ac.una.proyectoprogra.util.Permisos;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
    @FXML
    private MFXButton btnSubirWord;
    @FXML
    private Label lblTituloDocWord;
    @FXML
    private Label lblCantidadComodinesDetectados;

    @FXML
    private ListView<TipoInstRequisito> lstRequisitos;
    private ObservableList<TipoInstRequisito> listaRequisitos = FXCollections.observableArrayList();
    private List<TipoInstRequisito> requisitosEliminados = new ArrayList<>();   // si un requisito esta siendo usado por algun tipo de instrumento entonces no se deberia eliminar, el WS se encarga de validar esto ig
    private TipoInstrumento tipoInsSeleccionado;
    @FXML
    private VBox panelDerecho;
    @FXML
    private FlowPane flowPaneComodines;

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

        this.lstRequisitos.setItems(listaRequisitos);
        cargarTabla();

        panelDerecho.setDisable(true);
        //cuando clickeo en un elem de la tabla el metodo de mostrar detalle me deja editar el panel derecho
        tblTipoInstrumentos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> mostrarDetalle(nuevo));
        lstRequisitos.setCellFactory(lista -> new ListCell<TipoInstRequisito>() {

            // cada fila de la listview
            Button btnIndicadorDrag = new Button();
            Label lblNumero = new Label();
            Label lblTexto = new Label();
            Button btnEliminarRequisito = new Button("x");
            HBox fila = new HBox(8, btnIndicadorDrag, lblNumero, lblTexto, btnEliminarRequisito);

            {

                ImageView iconoDrag = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/drag.png")));
                iconoDrag.setFitWidth(20);
                iconoDrag.setFitHeight(20);

                btnIndicadorDrag.setGraphic(iconoDrag);
                btnIndicadorDrag.setMinWidth(20);

                fila.getStyleClass().add("filaRequisito");
                lblNumero.getStyleClass().add("numeroRequisito");
                lblTexto.getStyleClass().add("textoRequisito");
                btnEliminarRequisito.getStyleClass().add("btnEliminarRequisito");
                btnIndicadorDrag.getStyleClass().add("btnIndicadorDrag");

                fila.setAlignment(Pos.CENTER);
                HBox.setHgrow(lblTexto, Priority.ALWAYS);   // el texto ocupa el espacio sobrante
                lblTexto.setMaxWidth(Double.MAX_VALUE);
                lblTexto.setWrapText(true);

                btnEliminarRequisito.setOnAction(e -> eliminarRequisito(getIndex()));

                //1
                setOnDragDetected(event -> {

                    if (getItem() == null) {
                        return;
                    }

                    Dragboard db = startDragAndDrop(TransferMode.MOVE); // tiene la pos de esa fila
                    db.setDragView(fila.snapshot(null, null)); // mejorar talvez movindo toda la fila !to-do
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(String.valueOf(getIndex()));
                    db.setContent(cc);
                    event.consume();
                });

                //2 pasamos por encima
                setOnDragOver(event -> {
                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                // estos dos contribuye nd mas en la aestetica del programa
                setOnDragEntered(event -> {
                    // esta valid es para que no se resalte a si mismo hace que se se vea mejor nada mas    
                    if (getItem() != null && event.getGestureSource() != this) {
                        if (!getStyleClass().contains("dropTarget")) {
                            getStyleClass().add("dropTarget");
                        }
                    }

                });
                setOnDragExited(event -> getStyleClass().remove("dropTarget"));

                //3
                setOnDragDropped(event -> {

                    Dragboard db = event.getDragboard();
                    boolean completado = false;

                    if (db.hasString()) {
                        int desde = Integer.parseInt(db.getString()); // indice
                        TipoInstRequisito movido = listaRequisitos.remove(desde);

                        int hasta;
                        if (getItem() == null) {
                            hasta = listaRequisitos.size();
                        } else {
                            hasta = getIndex();
                        }

                        listaRequisitos.add(hasta, movido);
                        lstRequisitos.getSelectionModel().select(hasta);
                        completado = true;
                    }
                    event.setDropCompleted(completado);
                    event.consume();
                });
            }

            // si hay muchos requerimientos seria practico que se arresten y se escrolee al mismo tiempo
            @Override
            protected void updateItem(TipoInstRequisito requisito, boolean vacido) {
                super.updateItem(requisito, vacido);
                setText(null);

                if (vacido || requisito == null) {
                    setGraphic(null);   // nada que mostrar
                    return;
                }

                lblNumero.setText(String.valueOf(getIndex() + 1));
                lblTexto.setText(requisito.getTrqDescripcion());
                setGraphic(fila);
            }
        });

    }

    private void eliminarRequisito(int id) {

        TipoInstRequisito requisitoEliminado = listaRequisitos.remove(id);
        requisitosEliminados.add(requisitoEliminado);
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
        listaRequisitos.add(requisito);
        this.txtRequisito.clear();

    }

    @FXML
    private void onActionBtnGuardarCambios(ActionEvent event) {

        // el instrumento seleccionado sele asgina la lista de requisitos
        tipoInsSeleccionado.setTipoInstRequisitoList(new ArrayList<>(listaRequisitos));
        tipoInsSeleccionado.setTipoInstRequisitoEliminadosList(new ArrayList<>(requisitosEliminados));
        tipoInsSeleccionado.setTinBufeteId(bufeteCargado());

        // guardar el tipo de instrumentos ya tienen para actualizar los requisitos
        Respuesta respuesta = tipoInstrumentoService.guardarTipoInstrumento(tipoInsSeleccionado);
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            mostrarExito("Cambios guardados exitosamente!");
            Long idGuardado = tipoInsSeleccionado.getTinId();
            cargarTabla();
            seleccionarTipo(idGuardado);
        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    private Long bufeteCargado() {
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        return usuarioSesion.getUsrBufeteId();
    }
    private void seleccionarTipo(Long id) {
        for (TipoInstrumento tipoIns : listaTipoInstrumentos) {
            if (tipoIns.getTinId().equals(id)) {
                tblTipoInstrumentos.getSelectionModel().select(tipoIns);
                return;
            }
        }
    }

    private void mostrarDetalle(TipoInstrumento tipo) {
        tipoInsSeleccionado = tipo;
        requisitosEliminados.clear();

        if (tipo == null) {
            panelDerecho.setDisable(true);
            listaRequisitos.clear();
            limpiarMachote("");
            return;
        }

        panelDerecho.setDisable(false);
        listaRequisitos.setAll(tipo.getTipoInstRequisitoList());

        String ruta = tipo.getTinRutaMachote();
        if (ruta == null || ruta.isBlank()) {
            limpiarMachote("Sin machote");
        } else {
            // de la ruta completa del servidor solo mostramos el nombre del archivo
            lblTituloDocWord.setText(Paths.get(ruta).getFileName().toString());
            cargarComodines(tipo.getTinId());
        }
    }

    private void limpiarMachote(String titulo) {
        lblTituloDocWord.setText(titulo);
        lblCantidadComodinesDetectados.setText("0 comodines detectados");
        flowPaneComodines.getChildren().clear();
    }

    private void cargarTabla() {
        Respuesta respuesta = tipoInstrumentoService.getTipoInstrumentos(null, null, bufeteCargado());

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

        Function<String, Predicate<TipoInstrumento>> contieneTexto = x -> t -> t.getTinNombre().toLowerCase().contains(x);

        List<TipoInstrumento> listaFiltrada = todosLosTiposInstrumentos.stream()
                .filter(contieneTexto.apply(tinNombre))
                .filter(t -> coincide(t.getTinEstado(), cmbFiltroEstado))
                .collect(Collectors.toList());

        listaTipoInstrumentos.setAll(listaFiltrada);

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

        if (archivoSeleccionado == null) {
            return;   // el usuario canceló
        }

        if (!archivoSeleccionado.getName().toLowerCase().endsWith(".docx")) {
            mostrarError("El machote a subir tiene que ser un word .docx");
            return;
        }

        try {
            byte[] contenidoWord = Files.readAllBytes(archivoSeleccionado.toPath());
            Respuesta respuesta = tipoInstrumentoService.subirMachote(tipoInsSeleccionado.getTinId(), archivoSeleccionado.getName(), contenidoWord);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito("Se cargo el docx exitosamente");
                Long idTipoIns = tipoInsSeleccionado.getTinId();
                cargarTabla();
                seleccionarTipo(idTipoIns);
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } catch (IOException e) {
            mostrarError("Error no se pudo leer el archivo seleccionado");
        }

    }

    private void cargarComodines(Long tinId) {
        flowPaneComodines.getChildren().clear();
        Respuesta respuesta = tipoInstrumentoService.getComodines(tinId);

        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Comodin> listaComodines = (List<Comodin>) respuesta.getResultado("Comodines");
            this.lblCantidadComodinesDetectados.setText(listaComodines.size() + " comodines detectados");

            for (Comodin comodin : listaComodines) {
                Label label = new Label(comodin.getNombre());
                if (comodin.getValido()) {
                    label.getStyleClass().add("chipComodin");
                } else {
                    label.getStyleClass().add("chipComodinInvalido");
                }

                flowPaneComodines.getChildren().add(label);
            }

        } else {
            mostrarError(respuesta.getMensaje());
        }
    }

    @FXML
    private void onActionBtnVerComodinesDisponibles(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("ComodinesDisponiblesView", stage, true);
    }

}
