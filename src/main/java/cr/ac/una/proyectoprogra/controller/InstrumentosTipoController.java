/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.TipoInstrumento;
import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Formato;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtRequisito.setTextFormatter(Formato.getInstance().letrasFormat(25));
        txtBuscarInstrumento.setTextFormatter(Formato.getInstance().letrasFormat(100));

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

        this.tblTipoInstrumentos.setPlaceholder(new Label("No hay tipo de instrumentos por el momento"));

        cargarTabla();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnAgregarRequisito(ActionEvent event) {

        String requisito = this.txtRequisito.getText();

        if (requisito.isBlank() || requisito.isEmpty()) {
            mostrarError("El requisito no puede estar vacido");
        }

    }

    private void cargarTabla() {

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
    }
}
