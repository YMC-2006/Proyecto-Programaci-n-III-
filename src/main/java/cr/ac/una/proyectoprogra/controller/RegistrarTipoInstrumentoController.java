/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class RegistrarTipoInstrumentoController extends Controller implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private CheckBox chkEstado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;

    private TipoInstrumento tipoInstrumentoEditando = null;
    private TipoInstrumentoService tipoInstrumentoService = new TipoInstrumentoService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(50)); // en la BD tenemos 100 pero aca dejare 50
    }

    @Override
    public void initialize() {
        limpiarCampos();
        TipoInstrumento tipoInstrumento = (TipoInstrumento) AppContext.getInstance().get("tipoInstrumentoEditando");
        if(tipoInstrumento != null){
            cargarTipoInstrumento(tipoInstrumento);
            AppContext.getInstance().delete("tipoInstrumentoEditar");
        }
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        String nombreTipoIns = txtNombre.getText();
        boolean estado = chkEstado.isSelected();

        if (nombreTipoIns == null || nombreTipoIns.isBlank()) {
            mostrarError("El nombre del tipo de instrumento no puede estar vacido");
            return;
        }

        Long idBufete = 1L;
        if (tipoInstrumentoEditando == null) { // creando

            TipoInstrumento tipoInstrumento = new TipoInstrumento(nombreTipoIns, estado);
            tipoInstrumento.setTinBufeteId(idBufete);

            Respuesta respuesta = tipoInstrumentoService.guardarTipoInstrumento(tipoInstrumento);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito("Tipo Instrumento guardado con éxito");
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        } else { // editando
            tipoInstrumentoEditando.setTinNombre(nombreTipoIns);
            tipoInstrumentoEditando.setTinEstado(estado);
            tipoInstrumentoEditando.setTinBufeteId(idBufete);

            Respuesta respuesta = tipoInstrumentoService.guardarTipoInstrumento(tipoInstrumentoEditando);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito("Tipo Instrumento modificado con éxito");
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }
        }
    }

    private void limpiarCampos() {
        this.tipoInstrumentoEditando = null;
        this.txtNombre.clear();
        this.chkEstado.setSelected(false);
        this.btnAgregar.setText("Guardar");
    }

    private void cargarTipoInstrumento(TipoInstrumento tipoInstrumento) {
        tipoInstrumentoEditando = tipoInstrumento;
        this.txtNombre.setText(tipoInstrumento.getTinNombre());
        this.chkEstado.setSelected(tipoInstrumento.getTinEstado());
        this.btnAgregar.setText("Editar");

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
