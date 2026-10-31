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
import cr.ac.una.proyectoprogra.util.Formato;
import cr.ac.una.proyectoprogra.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXSpinner;
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
public class RegistrarSociedadController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtTomo;
    @FXML
    private TextField txtFolio;
    @FXML
    private TextField txtAsiento;
    @FXML
    private MFXDatePicker dtpFecha;
    @FXML
    private ComboBox<String> cbxAcciones;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTitulos;
    @FXML
    private TextField txtValor;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    
    private final String[] filtroAcciones = {"Comunes", "Preferentes"};
    private final String[] siglasTipo = {"C", "P"};
    @FXML
    private TextField txtDomicilio;
    
    private SociedadDto sociedadEditando;
    private SociedadService sociedadService = new SociedadService();
    
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxAcciones.getItems().setAll(filtroAcciones);
        cbxAcciones.getSelectionModel().selectFirst();

        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(30));
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(150));
        txtTelefono.setTextFormatter(Formato.getInstance().integerFormat(20));
        txtFolio.setTextFormatter(Formato.getInstance().integerFormat(20));
        txtTomo.setTextFormatter(Formato.getInstance().integerFormat(20));
        txtAsiento.setTextFormatter(Formato.getInstance().integerFormat(20));
        txtDomicilio.setTextFormatter(Formato.getInstance().maxLengthFormat(300));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtTitulos.setTextFormatter(Formato.getInstance().integerFormat(10));
        txtValor.setTextFormatter(Formato.getInstance().numeroDecimalFormat(14));
    }    

    @Override
    public void initialize() {
        limpiarCampos();
        SociedadDto sociedad = (SociedadDto) AppContext.getInstance().get("sociedadEditar");
        if (sociedad != null) {
            cargarSociedad(sociedad);
            AppContext.getInstance().delete("sociedadEditar");
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
    
    private void limpiarCampos() {
        cbxAcciones.getSelectionModel().selectFirst();
        txtCedula.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtDomicilio.clear();
        txtTomo.clear();
        txtFolio.clear();
        txtAsiento.clear();
        txtCorreo.clear();
        txtTitulos.clear();
        txtValor.clear();
        dtpFecha.clear();
        sociedadEditando = null;
        this.btnAgregar.setText("Guardar");
    }

    public void cargarSociedad(SociedadDto sociedad) {
        this.sociedadEditando = sociedad;

        int indiceTipo = Arrays.asList(siglasTipo).indexOf(sociedad.getSocTipoAcciones());
        if (indiceTipo >= 0) {
            cbxAcciones.getSelectionModel().select(indiceTipo);
        }
        txtCedula.setText(sociedad.getSocCedulaJuridica());
        txtNombre.setText(sociedad.getSocNombre());
        txtTelefono.setText(sociedad.getSocTelefono());
        txtFolio.setText(sociedad.getSocFolio());
        txtAsiento.setText(sociedad.getSocAsiento());
        txtTomo.setText(sociedad.getSocTomo());
        txtDomicilio.setText(sociedad.getSocDomicilio());
        txtCorreo.setText(sociedad.getSocCorreo());
        Long ct = sociedad.getSocCantidadTitulos();
        txtTitulos.setText(String.valueOf(ct));
        BigDecimal vt = sociedad.getSocValorTitulo();
        txtValor.setText(String.valueOf(vt));
        LocalDate fi = sociedad.getSocFechaInscripcion();
        dtpFecha.setValue(fi);

        this.btnAgregar.setText("Editar");
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        limpiarCampos();
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnAgregar(ActionEvent event) {
        int indiceTipo = cbxAcciones.getSelectionModel().getSelectedIndex();
        String cedula = txtCedula.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String domicilio = txtDomicilio.getText();
        String tomo = txtTomo.getText();
        String folio = txtFolio.getText();
        String asiento = txtAsiento.getText();
        String correo = txtCorreo.getText();
        String ct = txtTitulos.getText();
        Long titulos = Long.parseLong(ct);
        BigDecimal valor = new BigDecimal(txtValor.getText());
        LocalDate fechaI = dtpFecha.getValue();

        if (indiceTipo < 0) {
            mostrarError(FlowController.getIdioma().getString("sociedad.error.tipo"));
            cbxAcciones.requestFocus();
            return;
        }

        if (cedula.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("sociedad.error.cedula"));
            txtCedula.requestFocus();
            return;
        }

        if (nombre.isBlank()) {
            mostrarError(FlowController.getIdioma().getString("sociedad.error.nombre"));
            txtNombre.requestFocus();
            return;
        }


        if (sociedadEditando == null) {

            SociedadDto sociedad = new SociedadDto();
            sociedad.setSocTipoAcciones(siglasTipo[indiceTipo]);
            sociedad.setSocCedulaJuridica(cedula);
            sociedad.setSocNombre(nombre);
            sociedad.setSocDomicilio(domicilio);
            sociedad.setSocTomo(tomo);
            sociedad.setSocFolio(folio);
            sociedad.setSocAsiento(asiento);
            sociedad.setSocTelefono(telefono);
            sociedad.setSocCorreo(correo);
            sociedad.setSocCantidadTitulos(titulos);
            sociedad.setSocValorTitulo(valor);
            sociedad.setSocFechaInscripcion(fechaI);
            
            sociedad.setSocBufeteId(bufeteId);
            Respuesta respuesta = sociedadService.guardarSociedad(sociedad);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("sociedades.exito.guardar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        } else {

            sociedadEditando.setSocTipoAcciones(siglasTipo[indiceTipo]);
            sociedadEditando.setSocCedulaJuridica(cedula);
            sociedadEditando.setSocNombre(nombre);
            sociedadEditando.setSocDomicilio(domicilio);
            sociedadEditando.setSocTomo(tomo);
            sociedadEditando.setSocFolio(folio);
            sociedadEditando.setSocAsiento(asiento);
            sociedadEditando.setSocTelefono(telefono);
            sociedadEditando.setSocCorreo(correo);
            sociedadEditando.setSocCantidadTitulos(titulos);
            sociedadEditando.setSocValorTitulo(valor);
            sociedadEditando.setSocFechaInscripcion(fechaI);
            Respuesta respuesta = sociedadService.guardarSociedad(sociedadEditando);

            if (Boolean.TRUE.equals(respuesta.getEstado())) {
                mostrarExito(FlowController.getIdioma().getString("sociedades.exito.modificar"));
                limpiarCampos();
                ((Stage) btnAgregar.getScene().getWindow()).close();
            } else {
                mostrarError(respuesta.getMensaje());
            }

        }
    }
    
}
