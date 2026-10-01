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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.geometry.Bounds;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


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
    private PauseTransition esperaMantenimientos = new PauseTransition(Duration.millis(200));
    private PauseTransition esperaInstrumentos = new PauseTransition(Duration.millis(200));
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //menu de mantenimientos
        VBox opsMantenimiento = new VBox();
        opsMantenimiento.getStyleClass().add("menuDesplegable");
        opsMantenimiento.getChildren().addAll(
            crearBotonMenu("Abogados", "AbogadosView", menuMantenimientos),
            crearBotonMenu("Clientes", "ClientesView", menuMantenimientos),
            crearBotonMenu("Sociedades", "SociedadesView", menuMantenimientos)
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
        
    }

    private Button crearBotonMenu(String nombre, String vista, Popup menu) {
      Button boton = new Button(nombre);
      boton.getStyleClass().add("opcionMenu");
      boton.setMaxWidth(Double.MAX_VALUE);
      boton.setOnAction(e -> {
          menu.hide();
          FlowController.getInstance().goView(vista);
      });
      return boton;
    }
    
     private void mostrarMenu(Button boton, Popup menu, VBox opciones) {
        if(opciones.getStylesheets().isEmpty()){
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

}
