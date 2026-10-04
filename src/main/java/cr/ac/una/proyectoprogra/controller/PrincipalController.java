/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.geometry.Bounds;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class PrincipalController extends Controller implements Initializable {

    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' YYYY");

    @FXML
    private Button btnMantenimientos;
    @FXML
    private Button btnInstrumentos;

    private Popup menuMantenimientos = new Popup();
    private Popup menuInstrumentos = new Popup();

    // Temporizadores para cerrar cada menú con un pequeño retraso
    private PauseTransition esperaMantenimientos = new PauseTransition(Duration.millis(100));
    private PauseTransition esperaInstrumentos = new PauseTransition(Duration.millis(100));
    
    
    @FXML private Label fechaActualLabel;
    @FXML
    private VBox mainDisplay;
    @FXML
    private Button btnIndicadores;
    @FXML
    private Button btnEspanol;
    @FXML
    private Button btnIngles;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //menu de mantenimientos
       // mostrarFechaActual();
       ResourceBundle resB = rb;
        VBox opsMantenimiento = new VBox();
        opsMantenimiento.getStyleClass().add("menuDesplegable");
        opsMantenimiento.getChildren().addAll(
                crearBotonMenu(FlowController.getIdioma().getString("menu.abogados"), "AbogadosView","abogados", menuMantenimientos),
                crearBotonMenu(FlowController.getIdioma().getString("menu.clientes"), "ClientesView", "clientes", menuMantenimientos),
                crearBotonMenu(FlowController.getIdioma().getString("menu.sociedades"),"SociedadesView", "sociedades", menuMantenimientos)
        );

        menuMantenimientos.getContent().add(opsMantenimiento);

        esperaMantenimientos.setOnFinished(e -> menuMantenimientos.hide());
        btnMantenimientos.setOnMouseEntered(e -> {
            esperaMantenimientos.stop();
            menuInstrumentos.hide();
            mostrarMenu(btnMantenimientos, menuMantenimientos, opsMantenimiento);

        });

        btnMantenimientos.setOnMouseExited(e -> esperaMantenimientos.playFromStart());
        opsMantenimiento.setOnMouseEntered(e -> esperaMantenimientos.stop());
        opsMantenimiento.setOnMouseExited(e -> esperaMantenimientos.playFromStart());

        // menu de instrumentos
        VBox opsInstrumentos = new VBox();
        opsInstrumentos.getStyleClass().add("menuDesplegable");
        opsInstrumentos.getChildren().addAll(
            crearBotonMenu("Mantenimiento Instrumentos", "InstrumentoMantenimientoView", "instrumentos",  menuInstrumentos),
            crearBotonMenu("Estados Instrumentos", "InstrumentoMantenimientoEstadoView", "instrumentos",menuInstrumentos),
            crearBotonMenu("Control Instrumentos", "InstrumentoMantenimientoControlView", "instrumentos",menuInstrumentos)
        );

        menuInstrumentos.getContent().add(opsInstrumentos);

        esperaInstrumentos.setOnFinished(e -> menuInstrumentos.hide());
        btnInstrumentos.setOnMouseEntered(e -> {
            esperaInstrumentos.stop();
            menuMantenimientos.hide();
            mostrarMenu(btnInstrumentos, menuInstrumentos, opsInstrumentos);

        });

        btnInstrumentos.setOnMouseExited(e -> esperaInstrumentos.playFromStart());
        opsInstrumentos.setOnMouseEntered(e -> esperaInstrumentos.stop());
        opsInstrumentos.setOnMouseExited(e -> esperaInstrumentos.playFromStart());
        
        
        Platform.runLater(() -> {
            
            String vistaActual = FlowController.getVistaActual();
            if(vistaActual == null){
                FlowController.getInstance().goView("IndicadoresView");
            }else{
                FlowController.getInstance().goView(vistaActual);
            }
        });
    }

    private Button crearBotonMenu(String nombre, String vista, String icono, Popup menu) {
        Button boton = new Button(nombre);
        boton.getStyleClass().add("opcionMenu");
        boton.setMaxWidth(Double.MAX_VALUE);
        System.out.println("Cree boton " + nombre);
        
        Image img = new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/"+icono+".png"));
        ImageView imagen = new ImageView(img);
        imagen.setFitHeight(18);
        imagen.setFitWidth(18);
        
        boton.setGraphicTextGap(10);
        boton.setGraphic(imagen);
        
        boton.setOnAction(e -> {
            menu.hide();
            FlowController.getInstance().goView(vista);
        });
        return boton;
    }

    private void mostrarMenu(Button boton, Popup menu, VBox opciones) {
        if (opciones.getStylesheets().isEmpty()) {
            opciones.getStylesheets().addAll(boton.getScene().getRoot().getStylesheets());
        }

        // la pos de donde quiero que se vea el menu que es justo debajo de cualquiera de los botones ue tengan un desplegable
        Bounds pos = boton.localToScreen(boton.getBoundsInLocal());
        menu.show(boton, pos.getMinX(), pos.getMaxY());
    }
    
    
    // dura 1s en arrancar, to-do creo que con platform run later se puede arreglar
    private void mostrarFechaActual() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime ahora = LocalDateTime.now();
            fechaActualLabel.setText(ahora.format(formatoFecha));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void btnOnActionIndicadores(ActionEvent event) {
        FlowController.getInstance().goView("IndicadoresView");
    }

    @FXML
    private void btnOnActionAgenda(ActionEvent event) {
        FlowController.getInstance().goView("AgendaView");

    }

    @FXML
    private void btnOnActionContable(ActionEvent event) {
        FlowController.getInstance().goView("ContableView");

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void btnOnActionMantenimientos(ActionEvent event) {
    }

    @FXML
    private void btnOnActionInstrumentos(ActionEvent event) {
    }

    @FXML
    private void btnOnActionReportes(ActionEvent event) {
        FlowController.getInstance().goView("ReportesView");
    }

    @FXML
    private void btnOnActionEspanol(ActionEvent event) {
       
        FlowController.getInstance().cambiarIdioma("es");
    }

    @FXML
    private void btnOnActionIngles(ActionEvent event) {
        
        FlowController.getInstance().cambiarIdioma("en");
    }
    

}
