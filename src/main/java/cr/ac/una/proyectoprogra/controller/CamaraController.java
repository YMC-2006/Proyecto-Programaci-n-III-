/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import com.github.sarxos.webcam.Webcam;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class CamaraController extends Controller implements Initializable {

    @FXML
    private ImageView camaraFrame;
    @FXML
    private ImageView imgCamara;
    @FXML
    private ImageView imgCancelar;

    private Webcam webcam;
    private boolean isRunning = true;

    private RegistrarseController regCtrl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webcam = Webcam.getDefault();
        webcam.open();

        Thread thread = new Thread(() -> {
            while (isRunning) {
                try {
                    BufferedImage bf = webcam.getImage();
                    if (bf != null) {
                        WritableImage imagenVivo = SwingFXUtils.toFXImage(bf, null);
                        Platform.runLater(() -> camaraFrame.setImage(imagenVivo));
                    }
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void OnMouseClickedCapturar(MouseEvent event) {
        BufferedImage bf = webcam.getImage();
        Image foto = SwingFXUtils.toFXImage(bf, null);
        if (regCtrl != null) {
            regCtrl.setFotoCamara(foto);
        }
        Stage stage = (Stage) imgCamara.getScene().getWindow();
        webcam.close();
        stage.close();
    }

    @FXML
    private void OnMouseClickedCancelar(MouseEvent event) {
        webcam.close();
        ((Stage) imgCancelar.getScene().getWindow()).hide();
    }

    public void setControladorPrincipal(RegistrarseController ctrl) {
        this.regCtrl = ctrl;
    }

}