/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class RegistrarseController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtBufete;
    @FXML
    private MFXTextField txtTelefono;
    @FXML
    private MFXTextField txtCorreoBufete;
    @FXML
    private MFXTextField txtDireccion;
    @FXML
    private MFXComboBox<?> cbxTipoId;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtApellido1;
    @FXML
    private MFXTextField txtApellido2;
    @FXML
    private MFXTextField txtCelular;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXComboBox<?> cbxIdioma;
    @FXML
    private MFXPasswordField pswClave;
    @FXML
    private MFXPasswordField pswConfirmarClave;
    @FXML
    private ImageView imgBufete;
    @FXML
    private ImageView imgUsuarioAdmin;
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private MFXButton btnRegistrar;
    @FXML
    private ImageView imgBufeteExplorador;
    @FXML
    private ImageView imgUsuarioExplorador;
    @FXML
    private ImageView imgCamara;
    
    private byte[] fotoUsuario;
    private byte[] fotoBufete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }
    
    public void setFotoCamara(Image foto) {
        imgUsuarioAdmin.setImage(foto);
        fotoUsuario = convertirImageABytes(foto);
    }
    
    private byte[] convertirImageABytes(Image imagen) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(imagen, null);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            System.out.println("Error al convertir imagen de camara: " + e.getMessage());
            return null;
        }
    }


    @FXML
    private void OnActionBtnCancelar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("InicioSesionVista");
        ((Stage)btnCancelar.getScene().getWindow()).close();
        
    }

    @FXML
    private void OnActionBtnRegistrar(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("InicioSesionVista");
        ((Stage)btnRegistrar.getScene().getWindow()).close();
    }

    @FXML
    private void OnMouseClickedExploradorBufete(MouseEvent event) {
        
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Imagenes", "*.jpeg", "*.png", "*.jpg");
        FileChooser exploradorArchivos = new FileChooser();
        exploradorArchivos.setTitle("Explorador de Archivos");
        exploradorArchivos.getExtensionFilters().addAll(imgFilter);
        File archivoSeleccionado = exploradorArchivos.showOpenDialog(imgBufeteExplorador.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                fotoBufete = Files.readAllBytes(archivoSeleccionado.toPath());
                String rutaImg = archivoSeleccionado.toURI().toString();
                Image imagenBuf = new Image(rutaImg);
                imgBufete.setImage(imagenBuf);
            } catch (Exception e) {
                System.out.println("Error al leer la imagen" + e.getMessage());
            }

        }
        
    }

    @FXML
    private void OnMouseClickedExploradorUsuario(MouseEvent event) {
        
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Imagenes", "*.jpeg", "*.png", "*.jpg");
        FileChooser exploradorArchivos = new FileChooser();
        exploradorArchivos.setTitle("Explorador de Archivos");
        exploradorArchivos.getExtensionFilters().addAll(imgFilter);
        File archivoSeleccionado = exploradorArchivos.showOpenDialog(imgBufeteExplorador.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                fotoUsuario = Files.readAllBytes(archivoSeleccionado.toPath());
                String rutaImg = archivoSeleccionado.toURI().toString();
                Image imagenUsr = new Image(rutaImg);
                imgUsuarioAdmin.setImage(imagenUsr);
            } catch (Exception e) {
                System.out.println("Error al leer la imagen" + e.getMessage());
            }

        }
    }

    @FXML
    private void OnMouseClickedImgCamara(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cr/ac/una/proyectoprogra/view/CamaraView.fxml"));
            Parent root = loader.load();
            CamaraController camaraCtrl = loader.getController();
            camaraCtrl.setControladorPrincipal(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
