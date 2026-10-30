/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.MovimientoDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.ClienteService;
import cr.ac.una.proyectoprogra.service.MovimientoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nikole
 */
public class AgregarMovimientoController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private ComboBox<String> cbxTipo;
    @FXML
    private MFXDatePicker dtpFecha;
    @FXML
    private TextField txtMonto;
    @FXML
    private TextField txtFactura;
    @FXML
    private TextField txtDetalle;
    @FXML
    private MFXCheckbox chkTributa;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    
    private MovimientoDto movEditando;
    private MovimientoService movimientoService = new MovimientoService();

    private final String[] siglasTipo = {"I", "G"};
    private final String[] nombresTipo = {"Ingreso", "Gasto"};
    
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxTipo.getItems().setAll(nombresTipo);
        cbxTipo.getSelectionModel().selectFirst();

        txtMonto.setTextFormatter(Formato.getInstance().numeroDecimalFormat(14));
        txtFactura.setTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txtDetalle.setTextFormatter(Formato.getInstance().maxLengthFormat(300));
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
    
    private void limpiarCampos() {
        cbxTipo.getSelectionModel().selectFirst();
        txtMonto.clear();
        txtFactura.clear();
        txtDetalle.clear();
        dtpFecha.clear();
        chkTributa.setSelected(false);
        movEditando = null;
        this.btnAgregar.setText("Guardar");
    }
    
    public void cargarMovimiento(MovimientoDto mov) {
        this.movEditando = mov;

        int indiceTipo = Arrays.asList(siglasTipo).indexOf(mov.getMovTipo());
        if (indiceTipo >= 0) {
            cbxTipo.getSelectionModel().select(indiceTipo);
        }
        txtMonto.setText(mov.getMovMonto().toString());
        txtFactura.setText(mov.getMovFactura());
        txtDetalle.setText(mov.getMovDetalle());
        LocalDate fi = mov.getMovFecha();
        dtpFecha.setValue(fi);
        chkTributa.setSelected(mov.getMovTributa());

        this.btnAgregar.setText("Editar");
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        BigDecimal monto;
        int indiceTipo = cbxTipo.getSelectionModel().getSelectedIndex();
        String factura = txtFactura.getText();
        String detalle = txtDetalle.getText();
        LocalDate fecha = dtpFecha.getValue();
        Boolean tributa = chkTributa.isSelected();

        if (indiceTipo < 0) {
            mostrarError(FlowController.getIdioma().getString("movimiento.error.tipo"));
            cbxTipo.requestFocus();
            return;
        }

        if (factura.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("movimiento.error.factura"));
            txtFactura.requestFocus();
            return;
        }

        if (txtMonto.getText() == null || txtMonto.getText().isBlank()) {
            mostrarError(FlowController.getIdioma().getString("movimiento.error.monto"));
            txtMonto.requestFocus();
            return;
        }else{
            monto = new BigDecimal(txtMonto.getText());
        }
        
        if (fecha == null) {
            mostrarError(FlowController.getIdioma().getString("movimiento.error.fecha"));
            dtpFecha.requestFocus();
            return;
        }


        if (movEditando == null) {

            MovimientoDto movimiento = new MovimientoDto();
            movimiento.setMovTipo(siglasTipo[indiceTipo]);
            movimiento.setMovMonto(monto);
            movimiento.setMovFactura(factura);
            movimiento.setMovDetalle(detalle);
            movimiento.setMovTributa(tributa);
            movimiento.setMovFecha(fecha);
            
            movimiento.setMovBufeteId(bufeteId);
            Respuesta respuesta = movimientoService.guardarMovimiento(movimiento);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("contable.exito.guardar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else {

            movEditando.setMovTipo(siglasTipo[indiceTipo]);
            movEditando.setMovMonto(monto);
            movEditando.setMovFactura(factura);
            movEditando.setMovDetalle(detalle);
            movEditando.setMovTributa(tributa);
            movEditando.setMovFecha(fecha);
            Respuesta respuesta = movimientoService.guardarMovimiento(movEditando);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("contable.exito.modificar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        }
    }

    @Override
    public void initialize() {
        limpiarCampos();
        MovimientoDto mov = (MovimientoDto) AppContext.getInstance().get("movimientoEditar");
        if (mov != null) {
            cargarMovimiento(mov);
            AppContext.getInstance().delete("movimientoEditar");
        }
    }
    
}
