/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import cr.ac.una.proyectoprogra.util.Permisos;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class PrincipalController extends Controller implements Initializable {
    
    @FXML
    private Button btnMantenimientos;
    @FXML
    private Button btnInstrumentos;

    private Popup menuMantenimientos = new Popup();
    private Popup menuInstrumentos = new Popup();

    // Temporizadores para cerrar cada menú con un pequeño retraso
    private PauseTransition esperaMantenimientos = new PauseTransition(Duration.millis(100));
    private PauseTransition esperaInstrumentos = new PauseTransition(Duration.millis(100));

    @FXML
    private Label fechaActualLabel;
    @FXML
    private VBox mainDisplay;
    @FXML
    private Button btnIndicadores;
    @FXML
    private Button btnEspanol;
    @FXML
    private Button btnIngles;
    @FXML
    private Button btnCerrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        VBox opsMantenimiento = new VBox();
        opsMantenimiento.getStyleClass().add("menuDesplegable");
        if (Permisos.tienePermiso("AbogadosView", "Consultar")) {
            opsMantenimiento.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.abogados"), "AbogadosView", "abogados", menuMantenimientos));
        }
        if (Permisos.tienePermiso("ClientesView", "Consultar")) {
            opsMantenimiento.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.clientes"), "ClientesView", "clientes", menuMantenimientos));
        }
        if (Permisos.tienePermiso("UsuariosView", "Consultar")) {
            opsMantenimiento.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.usuarios"), "UsuariosView", "usuarios", menuMantenimientos));
        }
        if (Permisos.tienePermiso("SociedadesView", "Consultar")) {
            opsMantenimiento.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.sociedades"), "SociedadesView", "sociedades", menuMantenimientos));
        }
        if (Permisos.tienePermiso("ParametrosView", "Consultar")) {
            opsMantenimiento.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.parametros"), "ParametrosView", "parametros", menuMantenimientos));
        }

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

// Menú de instrumentos
        VBox opsInstrumentos = new VBox();
        opsInstrumentos.getStyleClass().add("menuDesplegable");

        if (Permisos.tienePermiso("InstrumentosTipoView", "Consultar")) {
            opsInstrumentos.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.tiposInstrumentos"),"InstrumentosTipoView","tipos_instrumento",menuInstrumentos));
        }

        if (Permisos.tienePermiso("InstrumentosEstadoView", "Consultar")) {
            opsInstrumentos.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.estadosInstrumentos"),"InstrumentosEstadoView","estados_instrumento",menuInstrumentos));
        }

        if (Permisos.tienePermiso("InstrumentosView", "Consultar")) {
            opsInstrumentos.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.controlInstrumentos"),"InstrumentosView","instrumentos",menuInstrumentos));
        }

        if (Permisos.tienePermiso("InstrumentosBusquedaView", "Consultar")) {
            opsInstrumentos.getChildren().add(crearBotonMenu(FlowController.getIdioma().getString("menu.buscarInstrumentos"),"InstrumentosBusquedaView","buscar",menuInstrumentos));
        }

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
            if (vistaActual != null && Permisos.tienePermiso(vistaActual, "Consultar")) {
                FlowController.getInstance().goView(vistaActual);
            } else {
                FlowController.getInstance().goView("IndicadoresView");
            }
        });
    }

    private Button crearBotonMenu(String nombre, String vista, String icono, Popup menu) {
        Button boton = new Button(nombre);
        boton.getStyleClass().add("opcionMenu");
        boton.setMaxWidth(Double.MAX_VALUE);

        Image img = new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/iconos_sicobu/" + icono + ".png"));
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

    @FXML
    private void btnOnActionCerrarSesion(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("InicioSesionVista");
        ((Stage) btnCerrar.getScene().getWindow()).close();
    }

}
