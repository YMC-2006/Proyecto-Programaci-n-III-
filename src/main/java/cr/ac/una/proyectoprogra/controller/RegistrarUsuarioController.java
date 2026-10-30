/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.model.UsuarioPermisoDto;
import cr.ac.una.proyectoprogra.service.UsuarioService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Respuesta;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.ByteArrayInputStream;

/**
 * FXML Controller class
 *
 * @author alond
 */
public class RegistrarUsuarioController extends Controller implements Initializable {

    @FXML
    private Label lblTituloModal;
    @FXML
    private ComboBox<String> cbxTipoId;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtApellido2;
    @FXML
    private ComboBox<String> cbxIdioma;
    @FXML
    private TextField txtCorreo;
    @FXML
    private ImageView imgFoto;
    @FXML
    private CheckBox chkAdmin;
    @FXML
    private TableView<UsuarioPermisoDto> tblPermisos;
    @FXML
    private TableColumn<UsuarioPermisoDto, String> colVista;
    @FXML
    private TableColumn<UsuarioPermisoDto, Boolean> colConsultar;
    @FXML
    private TableColumn<UsuarioPermisoDto, Boolean> colCrear;
    @FXML
    private TableColumn<UsuarioPermisoDto, Boolean> colModificar;
    @FXML
    private TableColumn<UsuarioPermisoDto, Boolean> colEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private ImageView imgUsuarioExplorador;
    @FXML
    private ImageView imgCamara;
    
    private byte[] fotoUsuario;
    
    private UsuarioDto usuarioEditando;
    private UsuarioService usuarioService = new UsuarioService();
    private ObservableList<UsuarioPermisoDto> listaPermisos = FXCollections.observableArrayList();

    private final String[] siglasTipoId = {"FIS", "DIM", "PAS", "NIT"};
    private final String[] nombresTipoId = {"Física nacional", "DIMEX", "Pasaporte", "NITE"};
    private final String[] siglasIdioma = {"ES", "EN"};
    private final String[] nombresIdioma = {"Español", "English"};
    private final String[] vistas = {"ClientesView", "AbogadosView", "SociedadesView", "ParametrosView", "InstrumentosTipoView", "InstrumentosEstadoView", "InstrumentosView", "InstrumentosBusquedaView"};
    private final String[] nombresVistas = {"Clientes", "Abogados", "Sociedades", "Parámetros", "Tipos de instrumento", "Estados de instrumento", "Control de instrumentos", "Búsqueda de instrumentos"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxTipoId.getItems().setAll(nombresTipoId);
        cbxIdioma.getItems().setAll(nombresIdioma);
 
        tblPermisos.setEditable(true);
        tblPermisos.setItems(listaPermisos);
        colVista.setCellValueFactory(data -> new SimpleStringProperty(nombreVista(data.getValue().getUpeVista())));
        colConsultar.setCellValueFactory(new PropertyValueFactory<>("upeConsultar"));
        colCrear.setCellValueFactory(new PropertyValueFactory<>("upeCrear"));
        colModificar.setCellValueFactory(new PropertyValueFactory<>("upeModificar"));
        colEliminar.setCellValueFactory(new PropertyValueFactory<>("upeEliminar"));
        colConsultar.setCellFactory(CheckBoxTableCell.forTableColumn(colConsultar));
        colCrear.setCellFactory(CheckBoxTableCell.forTableColumn(colCrear));
        colModificar.setCellFactory(CheckBoxTableCell.forTableColumn(colModificar));
        colEliminar.setCellFactory(CheckBoxTableCell.forTableColumn(colEliminar));
    }    


    @FXML
    private void onActionChkAdmin(ActionEvent event) {
        tblPermisos.setDisable(chkAdmin.isSelected());
    }

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        if (txtIdentificacion.getText().isBlank() || txtNombre.getText().isBlank() || txtApellido1.getText().isBlank()
            || txtUsuario.getText().isBlank() || txtCorreo.getText().isBlank()) {
        mostrarError("Complete la identificación, el nombre, el primer apellido, el usuario y el correo.");
        return;
        }
        UsuarioDto usuario;
        if (usuarioEditando == null) {
            usuario = new UsuarioDto();
        } else {
            usuario = usuarioEditando;
        }
        UsuarioDto usuarioSesion = (UsuarioDto) AppContext.getInstance().get("Usuario");
        usuario.setUsrBufeteId(usuarioSesion.getUsrBufeteId());
        usuario.setUsrTipoIdentificacion(siglasTipoId[cbxTipoId.getSelectionModel().getSelectedIndex()]);
        usuario.setUsrIdentificacion(txtIdentificacion.getText());
        usuario.setUsrNombre(txtNombre.getText());
        usuario.setUsrApellido1(txtApellido1.getText());
        usuario.setUsrApellido2(txtApellido2.getText());
        usuario.setUsrUsuario(txtUsuario.getText());
        usuario.setUsrCelular(txtCelular.getText());
        usuario.setUsrCorreo(txtCorreo.getText());
        usuario.setUsrIdioma(siglasIdioma[cbxIdioma.getSelectionModel().getSelectedIndex()]);
        usuario.setUsrFoto(fotoUsuario);
        usuario.setUsrAdmin(chkAdmin.isSelected());
        usuario.setUsrClave(null);
        if (chkAdmin.isSelected()) {
            usuario.setUsuarioPermisoList(new ArrayList<>());
        } else {
            usuario.setUsuarioPermisoList(new ArrayList<>(listaPermisos));
        }

        Respuesta respuesta = usuarioService.guardarUsuario(usuario);
        if (!respuesta.getEstado()) {
            mostrarError(respuesta.getMensaje());
            return;
        }
        if (usuarioEditando == null) {
            mostrarExito("El usuario se registró. Se le envió un correo para activar la cuenta.");
        } else {
            mostrarExito("El usuario se modificó correctamente.");
        }
        ((Stage) btnGuardar.getScene().getWindow()).close();
    }

    @Override
    public void initialize() {
        usuarioEditando = null;
        fotoUsuario = null;
        cbxTipoId.getSelectionModel().selectFirst();
        cbxIdioma.getSelectionModel().selectFirst();
        txtIdentificacion.clear();
        txtNombre.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtUsuario.clear();
        txtCelular.clear();
        txtCorreo.clear();
        imgFoto.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyectoprogra/resource/FotoPredeterminada.png")));
        chkAdmin.setSelected(false);
        tblPermisos.setDisable(false);
        lblTituloModal.setText("Registrar usuario");
        cargarPermisos(new ArrayList<>());
        UsuarioDto usuario = (UsuarioDto) AppContext.getInstance().get("usuarioEditar");
        if (usuario != null) {
            cargarUsuario(usuario);
            AppContext.getInstance().delete("usuarioEditar");
        }
    }
    
    private void cargarUsuario(UsuarioDto usuario) {
        System.out.println("Foto: " + usuario.getUsrFoto());
        usuarioEditando = usuario;
        int indiceTipo = Arrays.asList(siglasTipoId).indexOf(usuario.getUsrTipoIdentificacion());
        if (indiceTipo >= 0) {
            cbxTipoId.getSelectionModel().select(indiceTipo);
        }
        int indiceIdioma = Arrays.asList(siglasIdioma).indexOf(usuario.getUsrIdioma());
        if (indiceIdioma >= 0) {
            cbxIdioma.getSelectionModel().select(indiceIdioma);
        }
        txtIdentificacion.setText(usuario.getUsrIdentificacion());
        txtNombre.setText(usuario.getUsrNombre());
        txtApellido1.setText(usuario.getUsrApellido1());
        txtApellido2.setText(usuario.getUsrApellido2());
        txtUsuario.setText(usuario.getUsrUsuario());
        txtCelular.setText(usuario.getUsrCelular());
        txtCorreo.setText(usuario.getUsrCorreo());
        fotoUsuario = usuario.getUsrFoto();

        fotoUsuario = usuario.getUsrFoto();
        if (fotoUsuario != null) {
            Image foto = convertirBytesAImage(fotoUsuario);
            System.out.println("Bytes: " + fotoUsuario.length + " Error: " + foto.isError() + " Ancho: " + foto.getWidth());
            imgFoto.setImage(foto);
        }
        chkAdmin.setSelected(usuario.getUsrAdmin());
        tblPermisos.setDisable(usuario.getUsrAdmin());
        lblTituloModal.setText("Modificar usuario");
        cargarPermisos(usuario.getUsuarioPermisoList());
    }
    
    private Image convertirBytesAImage(byte[] bytes) {
        ByteArrayInputStream bytesFoto = new ByteArrayInputStream(bytes);
        return new Image(bytesFoto);
    }

    private void cargarPermisos(List<UsuarioPermisoDto> permisosUsuario) {
        listaPermisos.clear();
        for (int i = 0; i < vistas.length; i++) {
            UsuarioPermisoDto fila = new UsuarioPermisoDto(vistas[i]);
            for (int j = 0; j < permisosUsuario.size(); j++) {
                UsuarioPermisoDto permiso = permisosUsuario.get(j);
                if (permiso.getUpeVista().equals(vistas[i])) {
                    fila.setUpeId(permiso.getUpeId());
                    fila.setUpeConsultar(permiso.getUpeConsultar());
                    fila.setUpeCrear(permiso.getUpeCrear());
                    fila.setUpeModificar(permiso.getUpeModificar());
                    fila.setUpeEliminar(permiso.getUpeEliminar());
                }
            }
            listaPermisos.add(fila);
        }
    }

    private String nombreVista(String vista) {
        int indice = Arrays.asList(vistas).indexOf(vista);
        if (indice >= 0) {
            return nombresVistas[indice];
        }
        return vista;
    }
    
    public void setFotoCamara(Image foto) {
        imgFoto.setImage(foto);
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
    private void OnMouseClickedExploradorUsuario(MouseEvent event) {
        
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Imagenes", "*.jpeg", "*.png", "*.jpg");
        FileChooser exploradorArchivos = new FileChooser();
        exploradorArchivos.setTitle("Explorador de Archivos");
        exploradorArchivos.getExtensionFilters().addAll(imgFilter);
        File archivoSeleccionado = exploradorArchivos.showOpenDialog(imgUsuarioExplorador.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                fotoUsuario = Files.readAllBytes(archivoSeleccionado.toPath());
                String rutaImg = archivoSeleccionado.toURI().toString();
                Image imagenBuf = new Image(rutaImg);
                imgFoto.setImage(imagenBuf);
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
