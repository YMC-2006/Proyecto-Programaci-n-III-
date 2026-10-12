/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.EstadoInstrumento;
import cr.ac.una.proyectoprogra.service.EstadoInstrumentoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class RegistrarEstadoInstrumentoController extends Controller implements Initializable {

    @FXML
    private TextField txtDescripcion;
    @FXML
    private CheckBox chkActivo;
    @FXML
    private CheckBox chkNotifica;
    @FXML
    private CheckBox chkEstadoFinal;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;

    private EstadoInstrumentoService estadoInstrumentoService = new EstadoInstrumentoService();
    private EstadoInstrumento estadoInstrumentoEditando = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.txtDescripcion.setTextFormatter(Formato.getInstance().letrasFormat(30));
    }

    @Override
    public void initialize() {
        limpiarCampos();
        EstadoInstrumento estadoInstrumento = (EstadoInstrumento) AppContext.getInstance().get("estadoInstrumentoEditar");
        if (estadoInstrumento != null) {
            cargarEstadoInstrumento(estadoInstrumento);
            AppContext.getInstance().delete("estadoInstrumentoEditar");
        }
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {

        String descripcion = txtDescripcion.getText();
        boolean estado = chkActivo.isSelected();
        boolean notifica = chkNotifica.isSelected();
        boolean estadoFinal = chkEstadoFinal.isSelected();

        if (descripcion.isBlank() || descripcion.isEmpty()) {
            mostrarError("El campo de la descripción no puede ir vacido");
            return;
        }

        // los checks pueden ir seleccionados o no eso esta bien
        if (estadoInstrumentoEditando == null) { // modo crear
            Long idBufete = 1L;
            EstadoInstrumento estadoInstrumento = new EstadoInstrumento(descripcion, estado, notifica, estadoFinal);
            estadoInstrumento.setEinBufeteId(idBufete);

            Respuesta respuesta = estadoInstrumentoService.guardarEstadoInstrumento(estadoInstrumento);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito("Estado Instrumento guardado con éxito");
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else {  // modo editar  

            estadoInstrumentoEditando.setEinDescripcion(descripcion);
            estadoInstrumentoEditando.setEinActivo(estado);
            estadoInstrumentoEditando.setEinNotifica(notifica);
            estadoInstrumentoEditando.setEinFinal(estadoFinal);
            estadoInstrumentoEditando.setEinBufeteId(1L);

            Respuesta respuesta = estadoInstrumentoService.guardarEstadoInstrumento(estadoInstrumentoEditando);
            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito("Estado Instrumento modificado con éxito");
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        }

    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    private void limpiarCampos() {
        this.estadoInstrumentoEditando = null;

        this.txtDescripcion.clear();
        this.chkActivo.setSelected(false);
        this.chkNotifica.setSelected(false);
        this.chkEstadoFinal.setSelected(false);

        this.btnAgregar.setText("Guardar");
    }

    private void cargarEstadoInstrumento(EstadoInstrumento estadoInstrumento) {
        estadoInstrumentoEditando = estadoInstrumento;
        this.txtDescripcion.setText(estadoInstrumento.getEinDescripcion());
        this.chkActivo.setSelected(estadoInstrumento.getEinActivo());
        this.chkNotifica.setSelected(estadoInstrumento.getEinNotifica());
        this.chkEstadoFinal.setSelected(estadoInstrumento.getEinFinal());
        this.estadoInstrumentoEditando = estadoInstrumento;
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
