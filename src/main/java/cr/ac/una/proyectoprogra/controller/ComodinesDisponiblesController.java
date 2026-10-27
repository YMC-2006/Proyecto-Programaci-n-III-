/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Comodin;
import cr.ac.una.proyectoprogra.service.TipoInstrumentoService;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class ComodinesDisponiblesController extends Controller implements Initializable {

    @FXML
    private TableView<Comodin> tblComodines;
    @FXML
    private TableColumn<Comodin, String> colNombreComodin;
    @FXML
    private TableColumn<Comodin, String> colDescripcion;

    /**
     * Initializes the controller class.
     */
    private TipoInstrumentoService tipoInstrumentoService = new TipoInstrumentoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNombreComodin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tblComodines.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tblComodines.setPlaceholder(new Label("No hay comodines disponibles"));

        // al hacer doble clic en una fila, se copia el comodín para pegarlo en Word
        tblComodines.setOnMouseClicked(e -> {
            Comodin seleccionado = tblComodines.getSelectionModel().getSelectedItem();
            if (e.getClickCount() == 2 && seleccionado != null) {
                ClipboardContent contenido = new ClipboardContent();
                contenido.putString(seleccionado.getNombre());
                Clipboard.getSystemClipboard().setContent(contenido);
                mostrarExito(seleccionado.getNombre() + " copiado. Péguelo en su Word.");
            }
        });
    }

    @Override
    public void initialize() {   // cada vez que se abre el modal
        Respuesta respuesta = tipoInstrumentoService.getComodinesDisponibles();
        if (Boolean.TRUE.equals(respuesta.getEstado())) {
            List<Comodin> lista = (List<Comodin>) respuesta.getResultado("Comodines");
            tblComodines.setItems(FXCollections.observableArrayList(lista));
        } else {
            mostrarError(respuesta.getMensaje());
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
}
