package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Parametro;
import cr.ac.una.proyectoprogra.service.ParametroService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class RegistrarParametroController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label lblTituloModal;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btnExaminar;
    @FXML
    private TextArea txaDescripcion;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    private Parametro parametroEditando;
    private ParametroService parametroService = new ParametroService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // el codigo se escribe solo en mayusculas, sin espacios ni tildes (A-Z, 0-9 y _), maximo 50
        txtCodigo.setTextFormatter(new TextFormatter<String>(cambio -> {
            String texto = cambio.getText().toUpperCase().replaceAll("[^A-Z0-9_]", "");
            cambio.setText(texto);
            if (cambio.getControlNewText().length() > 50) {
                return null;
            }
            return cambio;
        }));

        txtValor.setTextFormatter(Formato.getInstance().maxLengthFormat(500));
        txaDescripcion.setTextFormatter(Formato.getInstance().maxLengthFormat(200));

        // si el codigo empieza con RUTA_ mostramos el boton para escoger carpeta
        txtCodigo.textProperty().addListener((obs, anterior, nuevo) -> actualizarBotonExaminar());
    }

    @Override
    public void initialize() {
        limpiarCampos();
        Parametro parametro = (Parametro) AppContext.getInstance().get("parametroEditar");
        if (parametro != null) {
            cargarParametro(parametro);
            AppContext.getInstance().delete("parametroEditar");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {

        String codigo = txtCodigo.getText();
        String valor = txtValor.getText();
        String descripcion = txaDescripcion.getText();

        if (codigo.isBlank()) {
            mostrarError(t("par.error.codigo"));
            txtCodigo.requestFocus();
            return;
        }

        if (valor.isBlank()) {
            mostrarError(t("par.error.valor"));
            txtValor.requestFocus();
            return;
        }

        // estoy creando
        if (parametroEditando == null) {

            Parametro parametro = new Parametro();
            parametro.setParCodigo(codigo);
            parametro.setParValor(valor.trim());
            parametro.setParDescripcion(descripcion.trim());
            parametro.setParBufeteId(1L);

            Respuesta respuesta = parametroService.guardarParametro(parametro);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(t("par.exito.guardar"));
                limpiarCampos();
                ((Stage) btnGuardar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else { // estoy editando (el codigo no se cambia)

            parametroEditando.setParValor(valor.trim());
            parametroEditando.setParDescripcion(descripcion.trim());
            parametroEditando.setParBufeteId(1L);

            Respuesta respuesta = parametroService.guardarParametro(parametroEditando);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(t("par.exito.modificar"));
                limpiarCampos();
                ((Stage) btnGuardar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }
    }

    @FXML
    private void onActionBtnExaminar(ActionEvent event) {
        DirectoryChooser explorador = new DirectoryChooser();
        explorador.setTitle(t("tin.machote.explorador"));

        // si ya hay una carpeta valida, el explorador abre ahi
        File actual = new File(txtValor.getText());
        if (actual.isDirectory()) {
            explorador.setInitialDirectory(actual);
        }

        File carpeta = explorador.showDialog(btnExaminar.getScene().getWindow());
        if (carpeta != null) {
            txtValor.setText(carpeta.getAbsolutePath());
        }
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    private void cargarParametro(Parametro parametro) {
        this.parametroEditando = parametro;

        txtCodigo.setText(parametro.getParCodigo());
        txtValor.setText(parametro.getParValor());
        txaDescripcion.setText(parametro.getParDescripcion());

        txtCodigo.setDisable(true);   // el WS busca el parametro por codigo, no se debe cambiar
        lblTituloModal.setText(t("par.modal.editar"));
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtValor.clear();
        txaDescripcion.clear();
        txtCodigo.setDisable(false);
        parametroEditando = null;
        lblTituloModal.setText(t("par.modal.titulo"));
        actualizarBotonExaminar();
    }

    private void actualizarBotonExaminar() {
        String codigo = txtCodigo.getText();
        boolean esRuta = codigo != null && codigo.startsWith("RUTA_");
        btnExaminar.setVisible(esRuta);
        btnExaminar.setManaged(esRuta);
    }

    private String t(String clave) {
        return FlowController.getIdioma().getString(clave);
    }

    public AnchorPane getRoot() {
        return root;
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}