/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.BusquedaInstrumento;
import cr.ac.una.proyectoprogra.model.ResultadoInstrumento;
import cr.ac.una.proyectoprogra.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class InstrumentosBusquedaController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAgregarFiltro;
    @FXML
    private MFXButton btnLimpiarFiltros;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnGenerarExcel;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colEscritura;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colTipo;
    @FXML
    private TableColumn<ResultadoInstrumento, LocalDateTime> colFecha;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colEstado;
    @FXML
    private TableColumn<ResultadoInstrumento, String> colParte;
    @FXML
    private TableColumn<ResultadoInstrumento, ResultadoInstrumento> colAcciones;
    @FXML
    private MFXScrollPane scrollPaneFiltros;
    @FXML
    private TableView<ResultadoInstrumento> tblFiltros;
    @FXML
    private VBox vboxFiltros;
    
    private DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private ResultadoInstrumento resultado;
    private List<BusquedaInstrumento> listaFiltros = new ArrayList<>();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colEscritura.setCellValueFactory(new PropertyValueFactory<>("rinEscritura"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("rinTipo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("rinEstado"));
        colParte.setCellValueFactory(new PropertyValueFactory<>("rinPartes"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("rinFecha"));
        colFecha.setCellFactory(columna -> new TableCell<ResultadoInstrumento, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime fecha, boolean empty) {
                super.updateItem(fecha, empty);
                if (empty || fecha == null) {
                    setText(null);
                    return;
                }
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                setText(fecha.format(formato));
            }
        });
        colAcciones.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue()));
        colAcciones.setCellFactory(columna -> new TableCell<ResultadoInstrumento, ResultadoInstrumento>() {
            private final Button btnAbrir = new Button();
            {
                ImageView icono = new ImageView(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/abrir.png")));
                icono.setFitHeight(30);
                icono.setFitWidth(30);
                btnAbrir.setGraphic(icono);
                btnAbrir.setStyle("-fx-background-color: transparent;");
                btnAbrir.setOnAction(e -> { 
                    ResultadoInstrumento resultado = getItem(); 
                    irInstrumento(resultado);
                });
            }
            @Override
            protected void updateItem(ResultadoInstrumento resultado, boolean empty) {
                super.updateItem(resultado, empty);
                if (empty || resultado == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(btnAbrir);
            }
        });
        
        tblFiltros.setRowFactory(tabla -> {
            TableRow<ResultadoInstrumento> fila = new TableRow<>();
            fila.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !fila.isEmpty()) {
                    irInstrumento(fila.getItem());
                }
            });
            return fila;
        });
    }    
    
    @Override
    public void initialize() {
        resultado = null;
    }

    // Chicas son de prueba estos datos es porque no he tocado el web servise so toca inventar
    private void agregarFiltro() {
        BusquedaInstrumento filtro = new BusquedaInstrumento();
        listaFiltros.add(filtro);

        ComboBox<String> cbxConector = new ComboBox<>();
        ComboBox<String> cbxCampo = new ComboBox<>();
        ComboBox<String> cbxComparacion = new ComboBox<>();
        ComboBox<String> cbxValor = new ComboBox<>();
        
        TextField txtValor = new TextField();
        DatePicker dpValor = new DatePicker();
        DatePicker dpValor2 = new DatePicker();
        Label lblY = new Label("y");
        HBox contenedorValor = new HBox(8);
        Button btnQuita = new Button("X");

        cbxConector.getStyleClass().add("comboBox");
        cbxCampo.getStyleClass().add("comboBox");
        cbxComparacion.getStyleClass().add("comboBox");
        cbxValor.getStyleClass().add("comboBox");
        txtValor.getStyleClass().add("txtCampo");
        dpValor.getStyleClass().add("dpCampo");
        dpValor2.getStyleClass().add("dpCampo");
        btnQuita.getStyleClass().add("btnX");
        dpValor.setPromptText("dd/mm/aaaa");
        dpValor2.setPromptText("dd/mm/aaaa");
        contenedorValor.setAlignment(Pos.CENTER_LEFT);
        cbxConector.getItems().addAll("Y", "O");
        cbxConector.setValue("Y");
        cbxConector.setPrefWidth(80);

        Runnable guardarFiltro = () -> {
            String campo = cbxCampo.getValue();
            String comparacion = cbxComparacion.getValue();
            filtro.setBinCondicion(cbxConector.getValue());
            filtro.setBinCampo(campo);
            filtro.setBinComparacion(comparacion);
            filtro.setBinRango1(null);
            filtro.setBinRango2(null);
            
            if (campo == null) {
                return;
            }

            if (campo.equalsIgnoreCase("Fecha")) {
                if (dpValor.getValue() != null) {
                    filtro.setBinRango1(dpValor.getValue().toString());
                }
                if ("entre".equals(comparacion) && dpValor2.getValue() != null) {
                    filtro.setBinRango2(dpValor2.getValue().toString());
                }
            } else if (campo.equalsIgnoreCase("Escritura") || campo.equalsIgnoreCase("Parte") || campo.equalsIgnoreCase("Detalle")) {
                filtro.setBinRango1(txtValor.getText());
            } else {
                filtro.setBinRango1(cbxValor.getValue());
            }
        };

        cbxConector.setOnAction(e -> guardarFiltro.run());
        cbxValor.setOnAction(e -> guardarFiltro.run());
        txtValor.textProperty().addListener((obs, viejo, nuevo) -> guardarFiltro.run());
        dpValor.valueProperty().addListener((obs, viejo, nuevo) -> guardarFiltro.run());
        dpValor2.valueProperty().addListener((obs, viejo, nuevo) -> guardarFiltro.run());

        cbxCampo.getItems().addAll("Tipo", "Estado", "Abogado", "Fecha", "Escritura", "Parte", "Detalle", "Índice notarial");
        cbxCampo.setOnAction(e -> {
            String campito = cbxCampo.getValue();
            if (campito == null) {
                return;
            }
            txtValor.clear();
            dpValor.setValue(null);
            dpValor2.setValue(null);
            cbxComparacion.getItems().clear();
            contenedorValor.getChildren().clear();

            if (campito.equalsIgnoreCase("Fecha")) {
                cbxComparacion.getItems().addAll("es el", "antes del", "despues del", "entre");
                contenedorValor.getChildren().add(dpValor);
            } else if (campito.equalsIgnoreCase("Escritura")) {
                cbxComparacion.getItems().addAll("es igual a");
                contenedorValor.getChildren().add(txtValor);
            } else if (campito.equalsIgnoreCase("Parte") || campito.equalsIgnoreCase("Detalle")) {
                cbxComparacion.getItems().addAll("contiene", "es igual a");
                contenedorValor.getChildren().add(txtValor);
            } else {
                cbxValor.getItems().clear();
                if (campito.equalsIgnoreCase("Tipo")) {
                    cbxComparacion.getItems().addAll("es", "no es");
                    cbxValor.getItems().addAll("Pobre", "Clase Media", "Alta");
                } else if (campito.equalsIgnoreCase("Estado")) {
                    cbxComparacion.getItems().addAll("es", "no es");
                    cbxValor.getItems().addAll("Libre", "Privado", "Hibrido");
                } else if (campito.equalsIgnoreCase("Abogado")) {
                    cbxComparacion.getItems().addAll("es", "no es");
                    cbxValor.getItems().addAll("Lic. Alondra Magic Winx", "Lic. Alondra Fashion Icon");
                } else if (campito.equalsIgnoreCase("Notarial")) {
                    cbxComparacion.getItems().addAll("es");
                    cbxValor.getItems().addAll("Sí", "No");
                }
                cbxValor.getSelectionModel().selectFirst();
                contenedorValor.getChildren().add(cbxValor);
            }
            cbxComparacion.getSelectionModel().selectFirst();
            guardarFiltro.run();
        });

        cbxComparacion.setOnAction(e -> {
            String comparacion = cbxComparacion.getValue();
            if (comparacion != null && "Fecha".equalsIgnoreCase(cbxCampo.getValue())) {
                contenedorValor.getChildren().clear();
                contenedorValor.getChildren().add(dpValor);
                if (comparacion.equals("entre")) {
                    contenedorValor.getChildren().addAll(lblY, dpValor2);
                } else {
                    dpValor2.setValue(null);
                }
            }
            guardarFiltro.run();
        });

        HBox filter = new HBox(10, cbxConector, cbxCampo, cbxComparacion, contenedorValor, btnQuita);
        filter.setAlignment(Pos.CENTER_LEFT);

        btnQuita.setOnAction(e -> {
            vboxFiltros.getChildren().remove(filter);
            listaFiltros.remove(filtro);
            actualizarConectores();
        });

        vboxFiltros.getChildren().add(filter);
        actualizarConectores();
        cbxCampo.getSelectionModel().selectFirst();
    }

   private void actualizarConectores() {
       if (vboxFiltros.getChildren().isEmpty()) {
           return;
       }
       HBox primeraFila = (HBox) vboxFiltros.getChildren().get(0);
       ComboBox<String> conector = (ComboBox<String>) primeraFila.getChildren().get(0);
       conector.setVisible(false);
   }

    private void irInstrumento(ResultadoInstrumento seleccionado) {
        resultado = seleccionado;
        getStage().close();
    }

    public ResultadoInstrumento getResultado() {
        return resultado;
    }
    
    @FXML
    private void onActionBtnAgregarFiltro(ActionEvent event) {
        agregarFiltro();
    }

    @FXML
    private void onActionBtnLimpiarFiltros(ActionEvent event) {
        vboxFiltros.getChildren().clear();
        listaFiltros.clear();
        tblFiltros.getItems().clear();
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        for (int i = 0; i < listaFiltros.size(); i++) {
            BusquedaInstrumento filtro = listaFiltros.get(i);
            boolean esEntre = "entre".equals(filtro.getBinComparacion());

            if (estaVacio(filtro.getBinCampo()) || estaVacio(filtro.getBinComparacion()) || estaVacio(filtro.getBinRango1()) || (esEntre && estaVacio(filtro.getBinRango2()))) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Búsqueda", getStage(), "Hay datos sin completar en los filtros. Revise.");
                return;
            }
            if (esEntre && filtro.getBinRango1().compareTo(filtro.getBinRango2()) > 0) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Búsqueda", getStage(), "Rango de fechas, la primera fecha no puede ser mayor que la segunda.");
                return;
            }
        }
        for (int i = 0; i < listaFiltros.size(); i++) {
            BusquedaInstrumento filtro = listaFiltros.get(i);
            System.out.println(filtro.getBinCondicion() + " | " + filtro.getBinCampo() + " | " + filtro.getBinComparacion() + " | " + filtro.getBinRango1() + " | " + filtro.getBinRango2());
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    @FXML
    private void onActionBtnGererarExcel(ActionEvent event) {
    }

}