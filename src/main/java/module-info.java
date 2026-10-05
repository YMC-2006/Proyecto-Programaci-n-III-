module cr.ac.una.proyectoprogra {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires MaterialFX;
    
    requires webcam.capture;
    
    requires java.logging;
    requires java.base;

    requires jakarta.ws.rs;
    requires jakarta.json.bind;    
    requires jakarta.json;
    
    opens cr.ac.una.proyectoprogra to javafx.fxml, MaterialFX;
    opens cr.ac.una.proyectoprogra.controller to javafx.fxml, javafx.graphics, MaterialFX;
    opens cr.ac.una.proyectoprogra.model;
    exports cr.ac.una.proyectoprogra;
}
