package cr.ac.una.proyectoprogra.util;

import cr.ac.una.proyectoprogra.controller.Controller;
import java.util.logging.Level;
import cr.ac.una.proyectoprogra.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import java.util.Locale;
import javafx.scene.layout.HBox;

public class FlowController {

    private static FlowController INSTANCE = null;
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();

    // para el idioma guardamos el nombre de la vista en la que estamos para luego cuando se cambia el idioma el sistema recarga en principal view y luego la vista en la que estabamos
    private static String vistaActual = null;

    private FlowController() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void InitializeFlow(Stage stage, ResourceBundle idioma) {
        getInstance();
        this.mainStage = stage;
        this.idioma = idioma;
    }

    private FXMLLoader getLoader(String name) {//Agarra el nombre de una vista y generar el loader de esa vista
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(App.class.getResource("view/" + name + ".fxml"), this.idioma);
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                        java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Creando loader [" + name + "].", ex);
                    }
                }
            }
        }
        return loader;
    }

    public void goMain() {//Manda a llamar la pantalla principal
        try {
            //FXMLLoader fXMLLoader= new FXMLLoader(App.class.getResource("view/PrincipalView.fxml"),this.idioma);
            //fXMLLoader.load();
            this.mainStage.setScene(new Scene(FXMLLoader.load(App.class.getResource("view/PrincipalView.fxml"), this.idioma)));
            MFXThemeManager.addOn(this.mainStage.getScene(), Themes.DEFAULT, Themes.LEGACY);
            this.mainStage.setMaximized(true);
            this.mainStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    public void goMain(String viewname) {
        try {
            this.mainStage.setScene(new Scene(FXMLLoader.load(App.class.getResource("view/" + viewname + ".fxml"), this.idioma)));
            MFXThemeManager.addOn(this.mainStage.getScene(), Themes.DEFAULT, Themes.LEGACY);
            this.mainStage.setMaximized(true);
            this.mainStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }

    }

    public void goView(String viewName) {
        goView(viewName, "Center", null);
    }

    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    public void goView(String viewName, String location, String accion) {
        if (location.equals("Center")) { // o sea si estoy en algun lado y ese lado es en una vista dentro del principal view ocupamos el nombre de esa vista
            vistaActual = viewName;
        }
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        //controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = this.mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":

                BorderPane borderPane = (BorderPane) stage.getScene().getRoot();
                VBox vBox = (VBox) borderPane.getCenter();
                vBox.getChildren().clear();
                vBox.getChildren().add(loader.getRoot());

                /*VBox vBox = ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter());
                vBox.getChildren().clear();
                vBox.getChildren().add(loader.getRoot());*/
                break;
            case "Top":
                BorderPane borderPane2 = (BorderPane) stage.getScene().getRoot();
                HBox hBox = (HBox) borderPane2.getTop();
                hBox.getChildren().clear();
                hBox.getChildren().add(loader.getRoot());
                break;
            case "Bottom":
                break;
            case "Right":
                break;
            case "Left":
                break;
            default:
                break;
        }
    }

    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
        MFXThemeManager.addOn(stage.getScene(), Themes.DEFAULT, Themes.LEGACY);

    }

    public void goViewInWindow(String viewName) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        //stage.setMaximized(true);
        // stage.getIcons().add(new Image("cr/ac/una/fairytaleadventures/resource/logo.png")); // TODO revisar que no reviente, si revento entonces se queda comentado -_-
        stage.setTitle(controller.getNombreVista());
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void goViewInWindowModal(String viewName, Stage parentStage, Boolean resizable) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.initialize();
        Stage stage = new Stage();
        /*stage.getIcons().add(new Image("cr/ac/una/unaplanilla/resource/LogoUNArojo.png"));*/
        stage.setTitle(controller.getNombreVista());
        stage.setResizable(resizable);
        stage.setOnHidden((WindowEvent event) -> {
            controller.getStage().getScene().setRoot(new Pane());
            controller.setStage(null);
            limpiarLoader(viewName);
        });
        controller.setStage(stage);
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.centerOnScreen();
        stage.showAndWait();

    }

    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    public void limpiarLoader(String view) {
        this.loaders.remove(view);
    }

    public static void setIdioma(ResourceBundle idioma) {
        FlowController.idioma = idioma;
    }

    public void initialize() {
        this.loaders.clear();
    }

    public void salir() {
        this.mainStage.close();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public static ResourceBundle getIdioma() {
        return idioma;
    }


    public static String getVistaActual(){
        return vistaActual;
    }

    public void cambiarIdioma(String idioma) {

        try {
            ResourceBundle nuevoIdioma = ResourceBundle.getBundle( "cr/ac/una/proyectoprogra/resource/idioma",Locale.of(idioma));
            FlowController.idioma = nuevoIdioma;
            loaders.clear();

            // Cargar nuevamente PrincipalView con el nuevo idioma
            FXMLLoader loader = new FXMLLoader(App.class.getResource("view/PrincipalView.fxml"),nuevoIdioma);

            Parent root = loader.load();
            mainStage.getScene().setRoot(root);
            mainStage.show();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE,"Error cambiando el idioma.",ex);
        }
    }

}
